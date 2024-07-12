package com.salmon.chatService.model.dto.chatSession;

import com.salmon.chatService.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
/**
 * <p>
 * 查询聊天会话信息请求
 * </p>
 *
 * @author Salmon
 * @since 2024-07-12
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Schema(name = "ChatSessionQueryRequest", description = "查询聊天会话信息请求")
public class ChatSessionQueryRequest extends PageRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Schema(description = "ID")
    private Integer id;

    @Schema(description = "会话ID")
    private String sessionId;

    @Schema(description = "最后收到的消息")
    private String lastMessage;

    @Schema(description = "最近接收消息时间（毫秒）")
    private Long lastReceiveTime;

    @Schema(description = "版本号")
    private Integer version;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    private LocalDateTime updateTime;

}