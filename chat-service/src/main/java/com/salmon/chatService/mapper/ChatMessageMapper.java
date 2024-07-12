package com.salmon.chatService.mapper;

import com.salmon.chatService.model.po.ChatMessage;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 聊天消息 Mapper 接口
 * </p>
 *
 * @author Salmon
 * @since 2024-07-12
 */
@Mapper
public interface ChatMessageMapper extends BaseMapper<ChatMessage> {

}
