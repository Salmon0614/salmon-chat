package com.salmon.chatService.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 通用返回类
 *
 * @author Salmon
 * @since 2024-05-19
 */
@Data
@Schema(name = "BaseResponse", description = "通用返回类")
public class BaseResponse<T> implements Serializable {

    @Schema(description = "状态码")
    private int code;

    @Schema(description = "响应数据")
    private T data;

    @Schema(description = "消息")
    private String message;

    @Schema(description = "code是否200")
    private Boolean isSuccess;

    public boolean isSuccess() {
        return this.code == ErrorCode.SUCCESS.getCode();
    }

    public BaseResponse(int code, T data, String message) {
        this.isSuccess = code == ErrorCode.SUCCESS.getCode();
        this.code = code;
        this.data = data;
        this.message = message;
    }

    public BaseResponse(int code, T data) {
        this.isSuccess = code == ErrorCode.SUCCESS.getCode();
        this.code = code;
        this.data = data;
    }

    public BaseResponse(ErrorCode errorCode) {
        this(errorCode.getCode(), null, errorCode.getMsg());
    }
}
