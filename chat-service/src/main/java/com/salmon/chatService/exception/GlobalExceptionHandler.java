package com.salmon.chatService.exception;

import com.salmon.chatService.common.BaseResponse;
import com.salmon.chatService.common.ErrorCode;
import com.salmon.chatService.common.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理
 *
 * @author Salmon
 * @since 2024-05-19
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 拦截所有参数校验异常请求
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public BaseResponse<?> methodArgumentNotValidException(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        log.warn("参数校验异常：{}", bindingResult.getFieldErrors().get(0).getDefaultMessage());
        return ResultUtils.validateFail(bindingResult);
    }

    /**
     * 处理业务异常
     */
    @ExceptionHandler(BusinessException.class)
    public BaseResponse<?> businessExceptionHandler(BusinessException e) {
        log.error("businessException: " + e.getMessage(), e);
        return ResultUtils.error(e.getCode(), e.getMessage());
    }

    /**
     * 处理运行时异常
     */
    @ExceptionHandler(RuntimeException.class)
    public BaseResponse<?> runtimeExceptionHandler(BusinessException e) {
        log.error("runtimeException: ", e);
        return ResultUtils.error(ErrorCode.SYSTEM_ERROR, e.getMessage());
    }

}
