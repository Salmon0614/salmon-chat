package com.salmon.chatService.service.impl;

import com.salmon.chatService.model.po.ChatSession;
import com.salmon.chatService.mapper.ChatSessionMapper;
import com.salmon.chatService.service.ChatSessionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 聊天会话信息 服务实现类
 * </p>
 *
 * @author Salmon
 * @since 2024-07-12
 */
@Service
public class ChatSessionServiceImpl extends ServiceImpl<ChatSessionMapper, ChatSession> implements ChatSessionService {

}
