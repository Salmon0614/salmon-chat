package com.salmon.chatService.common;

import lombok.Getter;

/**
 * 错误码枚举
 *
 * @author Salmon
 * @since 2024-05-19
 */
@Getter
public enum ErrorCode {

    SUCCESS(20000, "ok"),
    PARAMS_ERROR(40000, "请求参数错误"),
    NOT_LOGIN_ERROR(40100, "未登录"),
    NO_AUTH_ERROR(40101, "无权限"),
    NOT_FOUND_ERROR(40400, "请求数据不存在"),
    FORBIDDEN_ERROR(40300, "禁止访问"),
    SYSTEM_ERROR(50000, "系统内部异常，请联系管理员处理"),
    OPERATION_ERROR(50001, "操作失败，请联系管理员处理"),
    LIMIT_ERROR(50002, "请求过于频繁，请稍候再试");

    /**
     * 状态码
     */
    private final int code;
    /**
     * 信息
     */
    private final String msg;

    ErrorCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
