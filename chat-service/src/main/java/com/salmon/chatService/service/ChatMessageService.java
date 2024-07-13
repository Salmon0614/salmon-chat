package com.salmon.chatService.service;

import com.salmon.chatService.model.po.ChatMessage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.salmon.chatService.model.vo.chatMessage.ChatMessageVO;

import java.util.List;

/**
 * <p>
 * 聊天消息 服务类
 * </p>
 *
 * @author Salmon
 * @since 2024-07-12
 */
public interface ChatMessageService extends IService<ChatMessage> {

    /**
     * 查询聊天消息（群聊消息+单聊，单聊就查谁发给我的消息）
     *
     * @param contactAccountList 联系人账号集合
     * @param offlineTime        离线时间
     * @return 消息记录
     */
    List<ChatMessageVO> selectMessage(List<String> contactAccountList, Long offlineTime);
}
