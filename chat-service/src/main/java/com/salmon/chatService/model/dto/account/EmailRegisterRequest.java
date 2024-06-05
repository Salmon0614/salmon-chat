package com.salmon.chatService.model.dto.account;

import com.salmon.chatService.common.BaseCheckCode;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

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
    @Pattern(regexp = "^([A-Za-z0-9_\\-\\.])+\\@([A-Za-z0-9_\\-\\.])+\\.([A-Za-z]{2,4})$",message = "请输入正确的邮箱")
    private String email;

    @Schema(description = "密码")
    @NotBlank(message = "请输入密码")
    private String password;

    @Schema(description = "昵称")
    @NotBlank(message = "请填写昵称")
    @Length(max = 10, message = "昵称长度不能超过10位")
    private String nickname;
}
