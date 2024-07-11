package com.salmon.chatService.netty;

import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Salmon
 * @since 2024-07-11
 */
@Component
@Slf4j
public class ChannelContextUtils {

    private static final ConcurrentHashMap<Integer, Channel> USER_CONTEXT_MAP = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<Integer, ChannelGroup> GROUP_CONTEXT_MAP = new ConcurrentHashMap<>();

    @Resource
    private NettyService nettyService;

    /**
     * 将用户添加到上下文中
     *
     * @param userId  用户ID
     * @param channel 网络连接或传输通道
     */
    public void addContext(Integer userId, Channel channel) {
        String channelId = channel.id().toString();
        log.debug("userId:{} channelId:{}", userId, channelId);
        AttributeKey<Integer> attributeKey;
        if (!AttributeKey.exists(channelId)) {
            attributeKey = AttributeKey.newInstance(channelId);
        } else {
            attributeKey = AttributeKey.valueOf(channelId);
        }
        channel.attr(attributeKey).set(userId);
        USER_CONTEXT_MAP.put(userId, channel);
        // 连上后先发个心跳
        nettyService.saveUserHeartBeat(userId);

        // 模拟
        Integer groupId = 10000;
        addUserToGroup(groupId, channel);
    }

    /**
     * 将用户加入群聊
     *
     * @param groupId 群聊ID
     * @param channel 网络连接或传输通道
     */
    private void addUserToGroup(Integer groupId, Channel channel) {
        ChannelGroup channelGroup = GROUP_CONTEXT_MAP.get(groupId);
        if (Objects.isNull(channelGroup)) {
            channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
            GROUP_CONTEXT_MAP.put(groupId, channelGroup);
        }
        if (Objects.isNull(channel)) {
            return;
        }
        channelGroup.add(channel);
    }

    /**
     * 发送给指定用户
     *
     * @param fromId  发送者ID
     * @param toId    接受者ID
     * @param message 消息
     */
    public void sendToUser(Integer fromId, Integer toId, String message) {

    }

    /**
     * 发送消息到指定群聊
     */
    public void sendToGroup(Integer groupId, String message) {
        ChannelGroup channelGroup = GROUP_CONTEXT_MAP.get(groupId);
        channelGroup.writeAndFlush(new TextWebSocketFrame(message));
    }
}
