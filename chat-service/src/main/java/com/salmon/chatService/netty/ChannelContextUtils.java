package com.salmon.chatService.netty;

import cn.hutool.json.JSONUtil;
import com.salmon.chatService.common.ErrorCode;
import com.salmon.chatService.constant.Settings;
import com.salmon.chatService.exception.ThrowUtils;
import com.salmon.chatService.model.enums.chat.MessageTypeEnum;
import com.salmon.chatService.model.vo.chat.MessageSendVO;
import com.salmon.chatService.model.vo.chat.WsInitData;
import com.salmon.chatService.model.enums.contact.UserContactTypeEnum;
import com.salmon.chatService.model.po.User;
import com.salmon.chatService.model.vo.chatMessage.ChatMessageVO;
import com.salmon.chatService.model.vo.chatSessionUser.ChatSessionUserVO;
import com.salmon.chatService.service.ChatMessageService;
import com.salmon.chatService.service.ChatSessionUserService;
import com.salmon.chatService.service.UserContactApplyService;
import com.salmon.chatService.service.UserService;
import com.salmon.chatService.utils.Utils;
import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Salmon
 * @since 2024-07-11
 */
@Component
@Slf4j
public class ChannelContextUtils {

    private static final ConcurrentHashMap<String, Channel> USER_CONTEXT_MAP = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<String, ChannelGroup> GROUP_CONTEXT_MAP = new ConcurrentHashMap<>();

    @Resource
    private NettyService nettyService;
    @Resource
    private UserService userService;
    @Resource
    private ChatSessionUserService chatSessionUserService;
    @Resource
    private ChatMessageService chatMessageService;
    @Resource
    private UserContactApplyService userContactApplyService;

    /**
     * 将用户添加到上下文中
     *
     * @param account 用户账号
     * @param channel 网络连接或传输通道
     */
    @Transactional(rollbackFor = Exception.class)
    public void addContext(String account, Channel channel) {
        String channelId = channel.id().toString();
        log.debug("user account:{} channelId:{}", account, channelId);
        AttributeKey<String> attributeKey;
        if (!AttributeKey.exists(channelId)) {
            attributeKey = AttributeKey.newInstance(channelId);
        } else {
            attributeKey = AttributeKey.valueOf(channelId);
        }
        channel.attr(attributeKey).set(account);

        // 从缓存中查询所有联系人
        List<String> contactAccountList = userService.getContactList(account);
        List<String> groupNumberList = new ArrayList<>();
        for (String contactAccount : contactAccountList) {
            // 将用户的channel加入该群聊
            if (contactAccount.startsWith(UserContactTypeEnum.GROUP.getPrefix())) {
                addUserToGroup(contactAccount, channel);
                groupNumberList.add(contactAccount);
            }
        }
        USER_CONTEXT_MAP.put(account, channel);
        // 连上后先发个心跳
        nettyService.saveUserHeartBeat(account);

        // 更新用户最后连接时间
        // 更新登录状态
        User user = userService.getOneByAccount(account);
        user.setLastLoginTime(LocalDateTime.now());
        ThrowUtils.throwIf(!userService.updateById(user), ErrorCode.OPERATION_ERROR);

        // 处理离线时产生的数据
        Integer userId = user.getId();
        // 给用户发送消息（上线后查找离线时间内别人发给该用户的消息）
        Long lastOffTime = user.getLastOffTime(); // 离线时间戳
        Long offlineTime = lastOffTime;
        // 如果离线时间为空或者离线时间超过三天，则只查找三天内的离线消息
        if (lastOffTime == null || Utils.getCurrentTimestampInMillis() - Settings.OFFLINE_MESSAGE_EXPIRE_TIME > lastOffTime) {
            offlineTime = Settings.OFFLINE_MESSAGE_EXPIRE_TIME;
        }

        // 初始化socket数据
        WsInitData wsInitData = new WsInitData();

        // 1.查询用户所有会话消息（换设备登录消息也会同步，不会丢失）
        List<ChatSessionUserVO> chatSessionUserVOList = chatSessionUserService.selectSessionUserList(account);
        wsInitData.setChatSessionList(chatSessionUserVOList);

        // 2.查询聊天消息
        groupNumberList.add(account); // 群聊消息+单聊，单聊就查谁发给我的消息
        List<ChatMessageVO> chatMessageList = chatMessageService.selectMessage(groupNumberList, offlineTime);
        wsInitData.setChatMessageList(chatMessageList);

        // 3.查询好友申请
        Integer applyCount = userContactApplyService.selectNewApplyCountInOffline(userId, lastOffTime);
        wsInitData.setApplyCount(applyCount);

        // 发送消息，将初始化ws数据发送给当前登录连接ws的用户
        MessageSendVO<WsInitData> messageSendVO = new MessageSendVO<>();
        messageSendVO.setMessageType(MessageTypeEnum.INIT.getType());
        messageSendVO.setContactAccount(account);
        messageSendVO.setExtendData(wsInitData);
        sendMsg(messageSendVO, account);
    }

    /**
     * 将用户加入群聊
     *
     * @param groupNumber 群聊号
     * @param channel     网络连接或传输通道
     */
    private void addUserToGroup(String groupNumber, Channel channel) {
        ChannelGroup channelGroup = GROUP_CONTEXT_MAP.get(groupNumber);
        if (Objects.isNull(channelGroup)) {
            channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
            GROUP_CONTEXT_MAP.put(groupNumber, channelGroup);
        }
        if (Objects.isNull(channel)) {
            return;
        }
        channelGroup.add(channel);
    }

    /**
     * 发送消息
     *
     * @param messageSendVO  消息
     * @param receiveAccount 接收人账号
     */
    public static void sendMsg(MessageSendVO<?> messageSendVO, String receiveAccount) {
        if (!StringUtils.hasText(receiveAccount)) {
            return;
        }
        Channel sendChannel = USER_CONTEXT_MAP.get(receiveAccount);
        if (Objects.isNull(sendChannel)) {
            return;
        }
        // 相对于客户端而言，联系人就是发送人，这里转换下再发送
        messageSendVO.setContactAccount(messageSendVO.getSendUserAccount());
        messageSendVO.setContactNickname(messageSendVO.getContactNickname());
        sendChannel.writeAndFlush(new TextWebSocketFrame(JSONUtil.toJsonStr(messageSendVO)));
    }

    /**
     * 发送给指定用户
     *
     * @param sendAccount    发送者账号
     * @param contactAccount 接受者账号
     * @param message        消息
     */
    public void sendToUser(Integer sendAccount, Integer contactAccount, String message) {

    }

    /**
     * 发送消息到指定群聊
     *
     * @param groupNumber 群聊号
     * @param message     消息
     */
    public void sendToGroup(String groupNumber, String message) {
        ChannelGroup channelGroup = GROUP_CONTEXT_MAP.get(groupNumber);
        channelGroup.writeAndFlush(new TextWebSocketFrame(message));
    }

    /**
     * 移除用户上下文
     *
     * @param channel 网络连接或传输通道
     */
    public void removeContext(Channel channel) {
        Attribute<String> attribute = channel.attr(AttributeKey.valueOf(channel.id().toString()));
        String account = attribute.get();
        if (StringUtils.hasText(account)) {
            USER_CONTEXT_MAP.remove(account);
        }
        nettyService.removeUserHeartBeat(account);
        // 更新用户最后离线时间
        User user = userService.getOneByAccount(account);
        user.setLastOffTime(Utils.getCurrentTimestampInMillis());
        ThrowUtils.throwIf(!userService.updateById(user), ErrorCode.OPERATION_ERROR);
    }
}
