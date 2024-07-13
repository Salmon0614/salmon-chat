package com.salmon.chatService.model.vo.chat;

import com.salmon.chatService.model.vo.chatMessage.ChatMessageVO;
import com.salmon.chatService.model.vo.chatSessionUser.ChatSessionUserVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * websocket初始化数据
 *
 * @author Salmon
 * @since 2024-07-13
 */
@Data
@Schema(name = "WsInitData", description = "websocket初始化数据")
public class WsInitData implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "会话用户")
    private List<ChatSessionUserVO> chatSessionList;

    @Schema(description = "聊天消息")
    private List<ChatMessageVO> chatMessageList;

    @Schema(description = "申请好友数量")
    private Integer applyCount;
}
