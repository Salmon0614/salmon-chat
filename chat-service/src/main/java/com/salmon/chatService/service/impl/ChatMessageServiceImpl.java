package com.salmon.chatService.service.impl;

import com.salmon.chatService.model.po.ChatMessage;
import com.salmon.chatService.mapper.ChatMessageMapper;
import com.salmon.chatService.service.ChatMessageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 聊天消息 服务实现类
 * </p>
 *
 * @author Salmon
 * @since 2024-07-12
 */
@Service
public class ChatMessageServiceImpl extends ServiceImpl<ChatMessageMapper, ChatMessage> implements ChatMessageService {

}
