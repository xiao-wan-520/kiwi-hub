package com.iot.common.result;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 全局统一的返回结果
 * @author wan
 */
@SuppressWarnings("unused")
@Getter
@Setter
@NoArgsConstructor(staticName = "of")
public class Result<T> {
    private Integer code;

    private String message;

    private T data;

    public static <T> Result<T> success() {
        return Result.<T>of()
                .message(ResultCodeEnum.SUCCESS.getMessage())
                .code(ResultCodeEnum.SUCCESS.getCode());
    }

    public static <T> Result<T> success(T data) {
        return Result.<T>of()
                .message(ResultCodeEnum.SUCCESS.getMessage())
                .code(ResultCodeEnum.SUCCESS.getCode())
                .data(data);
    }

    public static <T> Result<T> fail() {
        return Result.<T>of()
                .message(ResultCodeEnum.BUSINESS_EXECUTION_FAIL.getMessage())
                .code(ResultCodeEnum.BUSINESS_EXECUTION_FAIL.getCode());
    }

    public static <T> Result<T> fail(T data) {
        return Result.<T>of()
                .message(ResultCodeEnum.BUSINESS_EXECUTION_FAIL.getMessage())
                .code(ResultCodeEnum.BUSINESS_EXECUTION_FAIL.getCode())
                .data(data);
    }

    public static <T> Result<T> result(ResultCodeEnum resultCodeEnum) {
        return Result.<T>of()
                .message(resultCodeEnum.getMessage())
                .code(resultCodeEnum.getCode());
    }

    public static <T> Result<T> result(ResultCodeEnum resultCodeEnum, T data) {
        return Result.<T>of()
                .message(resultCodeEnum.getMessage())
                .code(resultCodeEnum.getCode())
                .data(data);
    }

    public boolean isSuccess() {
        return this.code.equals(ResultCodeEnum.SUCCESS.getCode());
    }

    public Result<T> code(Integer code) {
        this.code = code;
        return this;
    }

    public Result<T> message(String message) {
        this.message = message;
        return this;
    }

    public Result<T> data(T data) {
        this.data = data;
        return this;
    }
}
