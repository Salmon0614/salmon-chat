package com.salmon.chatService.model.dto.chatSession;

import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serial;
import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
/**
 * <p>
 * 创建聊天会话信息请求
 * </p>
 *
 * @author Salmon
 * @since 2024-07-12
 */
@Data
@Schema(name = "ChatSessionAddRequest", description = "创建聊天会话信息请求")
public class ChatSessionAddRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "ID")
    @NotNull(message = "ID不能为空")
    private Integer id;

    @Schema(description = "会话ID")
    @NotBlank(message = "会话ID不能为空")
    private String sessionId;

    @Schema(description = "最后收到的消息")
    @NotBlank(message = "最后收到的消息不能为空")
    private String lastMessage;

    @Schema(description = "最近接收消息时间（毫秒）")
    @NotNull(message = "最近接收消息时间（毫秒）不能为空")
    private Long lastReceiveTime;

    @Schema(description = "版本号")
    @NotNull(message = "版本号不能为空")
    private Integer version;

    @Schema(description = "创建时间")
    @NotNull(message = "创建时间不能为空")
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    @NotNull(message = "更新时间不能为空")
    private LocalDateTime updateTime;

}