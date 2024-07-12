package com.salmon.chatService.service.impl;

import com.salmon.chatService.model.po.ChatSessionUser;
import com.salmon.chatService.mapper.ChatSessionUserMapper;
import com.salmon.chatService.service.ChatSessionUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 聊天会话用户 服务实现类
 * </p>
 *
 * @author Salmon
 * @since 2024-07-12
 */
@Service
public class ChatSessionUserServiceImpl extends ServiceImpl<ChatSessionUserMapper, ChatSessionUser> implements ChatSessionUserService {

}
