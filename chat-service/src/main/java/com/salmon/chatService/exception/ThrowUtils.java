package com.salmon.chatService.exception;

import com.salmon.chatService.common.ErrorCode;

/**
 * 抛异常工具类
 *
 * @author Salmon
 * @since 2024-05-19
 */
public class ThrowUtils {

    /**
     * 条件成立则抛异常
     *
     * @param condition        条件
     * @param runtimeException 运行时异常
     */
    public static void throwIf(boolean condition, RuntimeException runtimeException) {
        if (condition) {
            throw runtimeException;
        }
    }

    /**
     * 条件成立则抛业务异常
     *
     * @param condition 条件
     * @param errorCode 异常枚举
     */
    public static void throwIf(boolean condition, ErrorCode errorCode) {
        throwIf(condition, new BusinessException(errorCode));
    }

    /**
     * 条件成立则抛业务异常
     *
     * @param condition 条件
     * @param errorCode 异常枚举
     * @param message   异常信息
     */
    public static void throwIf(boolean condition, ErrorCode errorCode, String message) {
        throwIf(condition, new BusinessException(errorCode, message));
    }

}
