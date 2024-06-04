package com.salmon.chatService.controller;

import cn.hutool.core.lang.UUID;
import com.salmon.chatService.annotation.AccessLimit;
import com.salmon.chatService.common.BaseController;
import com.salmon.chatService.common.BaseResponse;
import com.salmon.chatService.common.ResultUtils;
import com.salmon.chatService.constant.RedisPrefixConstant;
import com.salmon.chatService.model.vo.account.CaptchaVo;
import com.salmon.chatService.utils.RedisUtils;
import com.wf.captcha.ArithmeticCaptcha;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 账号API
 *
 * @author Salmon
 * @since 2024-06-04
 */
@RestController
@RequestMapping("/account")
@Slf4j
@Tag(name = "AccountController", description = "账号API")
public class AccountController extends BaseController {

    @Operation(summary = "获取验证码")
    @AccessLimit
    @PostMapping("/getCheckCode")
    public BaseResponse<CaptchaVo> checkCode() {
        ArithmeticCaptcha captcha = new ArithmeticCaptcha(100, 43);
        String code = captcha.text();
        String checkCodeKey = UUID.randomUUID().toString();
        String captchaBase64 = captcha.toBase64();
        RedisUtils.set(RedisPrefixConstant.CHECK_CODE + checkCodeKey, code, RedisPrefixConstant.CODE_EXPIRE_TIME);
        return ResultUtils.success(CaptchaVo.builder().base64(captchaBase64).codeKey(checkCodeKey).build());
    }
}
