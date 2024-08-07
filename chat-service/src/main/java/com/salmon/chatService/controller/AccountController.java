package com.salmon.chatService.controller;

import cn.hutool.core.lang.UUID;
import com.salmon.chatService.annotation.AccessLimit;
import com.salmon.chatService.annotation.CheckCode;
import com.salmon.chatService.common.BaseController;
import com.salmon.chatService.common.BaseResponse;
import com.salmon.chatService.common.ResultUtils;
import com.salmon.chatService.constant.RedisPrefixConstant;
import com.salmon.chatService.model.dto.account.EmailLoginRequest;
import com.salmon.chatService.model.dto.account.EmailRegisterRequest;
import com.salmon.chatService.model.dto.account.ForgetPasswordRequest;
import com.salmon.chatService.model.vo.account.CaptchaVo;
import com.salmon.chatService.model.vo.user.UserVO;
import com.salmon.chatService.service.UserService;
import com.salmon.chatService.utils.RedisUtils;
import com.wf.captcha.ArithmeticCaptcha;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

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

    @Resource
    private UserService userService;

    @Operation(summary = "获取验证码")
    @AccessLimit(maxCount = 10)
    @PostMapping("/getCheckCode")
    public BaseResponse<CaptchaVo> checkCode() {
        ArithmeticCaptcha captcha = new ArithmeticCaptcha(100, 43);
        String code = captcha.text();
        String checkCodeKey = UUID.randomUUID().toString();
        String captchaBase64 = captcha.toBase64();
        RedisUtils.set(RedisPrefixConstant.CHECK_CODE + checkCodeKey, code, RedisPrefixConstant.CODE_EXPIRE_TIME);
        return ResultUtils.success(CaptchaVo.builder().base64(captchaBase64).codeKey(checkCodeKey).build());
    }

    @Operation(summary = "通过邮箱进行注册")
    @PostMapping("/registerByEmail")
    @CheckCode
    public BaseResponse<?> registerByEmail(@RequestBody @Valid EmailRegisterRequest request) {
        userService.register(request);
        return ResultUtils.success();
    }

    @Operation(summary = "通过邮箱进行登录")
    @PostMapping("/loginByEmail")
    @CheckCode
    public BaseResponse<UserVO> loginByEmail(@RequestBody @Valid EmailLoginRequest request) {
        UserVO userVO = userService.login(request);
        return ResultUtils.success(userVO);
    }

    @Operation(summary = "找回密码")
    @PostMapping("/forgetPassword")
    @CheckCode
    public BaseResponse<?> forgetPassword(@RequestBody @Valid ForgetPasswordRequest request) {
        userService.forgetPassword(request);
        return ResultUtils.success();
    }

    @Operation(summary = "退出登录")
    @PostMapping("/logout")
    @CheckCode
    public BaseResponse<?> logout() {
        // todo 退出登录，关闭ws连接
        return ResultUtils.success();
    }
}
