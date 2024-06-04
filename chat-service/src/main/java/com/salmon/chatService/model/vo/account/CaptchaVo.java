package com.salmon.chatService.model.vo.account;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

/**
 * 图形验证码视图
 *
 * @author Salmon
 * @since 2024-06-04
 */
@Data
@Builder
@Schema(name = "CaptchaVo", description = "图形验证码视图")
public class CaptchaVo {

    @Schema(description = "base64图片")
    private String base64;

    @Schema(description = "验证码key")
    private String codeKey;
}
