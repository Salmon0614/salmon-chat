package com.salmon.chatService.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 验证码基类
 *
 * @author Salmon
 * @since 2024-06-05
 */
@Data
@Schema(name = "BaseCheckCode", description = "验证码基类")
public class BaseCheckCode implements Serializable {

    @Schema(description = "验证码标识")
    @NotBlank(message = "验证码标识不能为空")
    private String checkCodeKey;

    @Schema(description = "验证码")
    @NotBlank(message = "请输入验证码")
    private String checkCode;
}
