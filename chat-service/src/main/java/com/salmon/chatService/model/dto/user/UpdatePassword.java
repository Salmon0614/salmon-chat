package com.salmon.chatService.model.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serial;
import java.io.Serializable;

/**
 * 修改密码请求
 *
 * @author Salmon
 * @since 2024-06-10
 */
@Data
@Schema(name = "UpdatePassword", description = "修改密码请求")
public class UpdatePassword implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @NotBlank(message = "请输入新密码")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$", message = "密码至少8个字符，包含大小写字母和数字")
    @Schema(description = "密码")
    private String password;
}
