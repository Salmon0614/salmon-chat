package com.salmon.chatService.exception;

import com.salmon.chatService.common.ErrorCode;
import lombok.Getter;

/**
 * 业务异常
 *
 * @author Salmon
 * @since 2024-05-19
 */
@Getter
public class BusinessException extends RuntimeException {

    private final int code;

    public BusinessException(int code, String message) {
        super(message);
        this.code = code;
    }

    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getMsg());
        this.code = errorCode.getCode();
    }

    public BusinessException(ErrorCode errorCode, String message) {
        super(message);
        this.code = errorCode.getCode();
    }
}
