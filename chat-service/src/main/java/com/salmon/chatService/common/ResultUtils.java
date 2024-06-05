package com.salmon.chatService.common;

import org.springframework.validation.BindingResult;

/**
 * 返回工具类
 *
 * @author Salmon
 * @since 2024-05-19
 */
public class ResultUtils {

    /**
     * 成功
     */
    public static <T> BaseResponse<T> success() {
        return new BaseResponse<>(0, null, "ok");
    }

    /**
     * 成功
     *
     * @param data 响应数据
     */
    public static <T> BaseResponse<T> success(T data) {
        return new BaseResponse<>(0, data, "ok");
    }

    /**
     * 失败
     *
     * @param errorCode 异常枚举
     */
    public static <T> BaseResponse<T> error(ErrorCode errorCode) {
        return new BaseResponse<>(errorCode);
    }

    /**
     * 失败
     *
     * @param code    异常码
     * @param message 异常信息
     */
    public static <T> BaseResponse<T> error(int code, String message) {
        return new BaseResponse<>(code, null, message);
    }

    /**
     * 失败
     *
     * @param errorCode 异常码
     * @param message   指定异常信息
     */
    public static <T> BaseResponse<T> error(ErrorCode errorCode, String message) {
        return new BaseResponse<>(errorCode.getCode(), null, message);
    }


    /**
     * 未登录
     */
    public static <T> BaseResponse<T> notLogin() {
        return error(ErrorCode.NOT_LOGIN_ERROR);
    }

    /**
     * 未权限
     */
    public static <T> BaseResponse<T> notAuth() {
        return error(ErrorCode.NO_AUTH_ERROR);
    }

    /**
     * 通用错误--操作失败
     */
    public static <T> BaseResponse<T> commonError() {
        return error(ErrorCode.OPERATION_ERROR);
    }

    /**
     * 参数校验失败的返回
     */
    public static <T> BaseResponse<T> validateFail(BindingResult bindingResult) {
        return error(ErrorCode.PARAMS_ERROR, bindingResult.getAllErrors().get(0).getDefaultMessage());
    }
}
