package com.salmon.chatService.model.dto.userBeauty;

import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 更新靓号请求
 * </p>
 *
 * @author Salmon
 * @since 2024-06-05
 */
@Data
@Schema(name = "UserBeautyUpdateRequest", description = "更新靓号请求")
public class UserBeautyUpdateRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "ID")
    @NotNull(message = "ID不能为空")
    private Integer id;

    @Schema(description = "邮箱")
    @NotBlank(message = "邮箱不能为空")
    private String email;

    @Schema(description = "靓号")
    @NotBlank(message = "靓号不能为空")
    private String account;
}