package com.salmon.chatService.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.salmon.chatService.mapper.ChatMessageMapper;
import com.salmon.chatService.model.po.ChatMessage;
import com.salmon.chatService.model.vo.chatMessage.ChatMessageVO;
import com.salmon.chatService.service.ChatMessageService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public List<ChatMessageVO> selectMessage(List<String> contactAccountList, Long offlineTime) {
        List<ChatMessage> chatMessages = this.list(new LambdaQueryWrapper<ChatMessage>()
                .in(ChatMessage::getContactAccount, contactAccountList)
                .ge(ChatMessage::getSendTime, offlineTime)
        );
        if (CollectionUtils.isEmpty(chatMessages)) {
            return new ArrayList<>();
        }
        return chatMessages.stream().map(ChatMessageVO::objToVo).collect(Collectors.toList());
    }
}
