package com.salmon.chatService.model.dto.account;

import com.salmon.chatService.common.BaseCheckCode;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;

/**
 * 注册（邮箱方式）
 *
 * @author Salmon
 * @since 2024-06-05
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Schema(name = "EmailRegister", description = "注册（邮箱方式）")
public class EmailRegisterRequest extends BaseCheckCode {

    @Schema(description = "邮箱")
    @NotBlank(message = "请输入邮箱")
    @Email(message = "请输入正确的邮箱")
    private String email;

    @Schema(description = "密码")
    @NotBlank(message = "请输入密码")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$", message = "密码至少8个字符，包含大小写字母和数字")
    private String password;

    @Schema(description = "昵称")
    @NotBlank(message = "请填写昵称")
    @Length(min = 2, max = 10, message = "昵称长度在2~10之间")
    private String nickname;
}
