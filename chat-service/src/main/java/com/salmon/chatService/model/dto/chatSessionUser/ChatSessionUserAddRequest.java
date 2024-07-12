package com.salmon.chatService.model.dto.chatSessionUser;

import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serial;
import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
/**
 * <p>
 * 创建聊天会话用户请求
 * </p>
 *
 * @author Salmon
 * @since 2024-07-12
 */
@Data
@Schema(name = "ChatSessionUserAddRequest", description = "创建聊天会话用户请求")
public class ChatSessionUserAddRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "id")
    @NotNull(message = "id不能为空")
    private Integer id;

    @Schema(description = "用户账号")
    @NotBlank(message = "用户账号不能为空")
    private String userAccount;

    @Schema(description = "联系人账号")
    @NotBlank(message = "联系人账号不能为空")
    private String contactAccount;

    @Schema(description = "会话ID")
    @NotBlank(message = "会话ID不能为空")
    private String sessionId;

    @Schema(description = "联系人名称")
    @NotBlank(message = "联系人名称不能为空")
    private String contactName;

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