package com.salmon.chatService.model.dto.account;

import com.salmon.chatService.common.BaseCheckCode;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * 邮箱登录
 *
 * @author Salmon
 * @since 2024-06-05
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Schema(name = "EmailLogin", description = "邮箱登录")
public class EmailLoginRequest extends BaseCheckCode {

    @Schema(description = "邮箱")
    @NotBlank(message = "请输入邮箱")
    @Email
    private String email;

    @Schema(description = "密码")
    @NotBlank(message = "请输入密码")
    private String password;
}
