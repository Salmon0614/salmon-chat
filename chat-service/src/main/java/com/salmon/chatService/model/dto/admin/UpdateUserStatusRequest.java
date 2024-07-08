package com.salmon.chatService.model.dto.admin;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serial;
import java.io.Serializable;

/**
 * 更新用户状态请求
 *
 * @author Salmon
 * @since 2024-07-08
 */
@Data
@Schema(name = "UpdateUserStatusRequest", description = "更新用户状态请求")
public class UpdateUserStatusRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "用户ID")
    @NotNull(message = "请选择用户")
    private Integer userId;

    @Schema(description = "状态")
    @NotNull(message = "请选择状态")
    private Integer status;
}
