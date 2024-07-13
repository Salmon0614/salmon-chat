package com.salmon.chatService.model.dto.chatMessage;

import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serial;
import java.io.Serializable;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * <p>
 * 更新聊天消息请求
 * </p>
 *
 * @author Salmon
 * @since 2024-07-12
 */
@Data
@Schema(name = "ChatMessageUpdateRequest", description = "更新聊天消息请求")
public class ChatMessageUpdateRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "ID")
    @NotNull(message = "ID不能为空")
    @Min(value = 1)
    private Long id;

    @Schema(description = "会话ID")
    @NotBlank(message = "会话ID不能为空")
    private String sessionId;

    @Schema(description = "消息类型")
    @NotNull(message = "消息类型不能为空")
    private Integer messageType;

    @Schema(description = "消息内容")
    @NotBlank(message = "消息内容不能为空")
    private String messageContent;

    @Schema(description = "发送人账号")
    @NotBlank(message = "发送人账号不能为空")
    private String sendUserAccount;

    @Schema(description = "发送人昵称")
    @NotBlank(message = "发送人昵称不能为空")
    private String sendUserNickname;

    @Schema(description = "发送时间")
    @NotNull(message = "发送时间不能为空")
    private Long sendTime;

    @Schema(description = "接收联系人账号")
    @NotBlank(message = "接收联系人账号不能为空")
    private String contactAccount;

    @Schema(description = "联系人类型 0-用户 1-群聊")
    @NotNull(message = "联系人类型 0-用户 1-群聊不能为空")
    private Integer contactType;

    @Schema(description = "文件大小")
    @NotNull(message = "文件大小不能为空")
    private Long fileSize;

    @Schema(description = "文件名称")
    @NotBlank(message = "文件名称不能为空")
    private String fileName;

    @Schema(description = "文件类型")
    @NotNull(message = "文件类型不能为空")
    private Integer fileType;

    @Schema(description = "状态 0-正在发送 1-已发送")
    @NotNull(message = "状态 0-正在发送 1-已发送不能为空")
    private Integer status;

    @Schema(description = "版本号")
    @NotNull(message = "版本号不能为空")
    private Integer version;

    @Schema(description = "创建时间")
    @NotNull(message = "创建时间不能为空")
    private LocalDateTime createTime;

    @Schema(description = "修改时间")
    @NotNull(message = "修改时间不能为空")
    private LocalDateTime updateTime;

}