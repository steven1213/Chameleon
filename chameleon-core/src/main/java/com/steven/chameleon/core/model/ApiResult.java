package com.steven.chameleon.core.model;

import com.steven.chameleon.core.util.RequestIdGenerator;
import lombok.Getter;

import java.io.Serial;
import java.io.Serializable;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;


@Getter
@SuppressWarnings("unchecked")
public class ApiResult<T> implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    
    private int code;
    private String message;
    private T data;
    private final Long timestamp;
    private String requestId;  // 请求追踪ID
    
    private ApiResult() {
        this.timestamp = System.currentTimeMillis();
        this.requestId = RequestIdGenerator.getOrGenerate();
    }
    
    // 静态构建方法
    public static <T> ApiResult<T> of(IResultCode resultCode) {
        return new ApiResult<T>()
            .setCode(resultCode.getCode())
            .setMessage(resultCode.getMessage());
    }
    
    public static <T> ApiResult<T> of(IResultCode resultCode, T data) {
        return new ApiResult<T>()
                .setCode(resultCode.getCode())
                .setMessage(resultCode.getMessage())
                .setData(data);
    }
    
    // 成功响应
    public static <T> ApiResult<T> ok() {
        return of(GlobalResultCode.SUCCESS);
    }
    
    public static <T> ApiResult<T> ok(T data) {
        return of(GlobalResultCode.SUCCESS, data);
    }
    
    // 失败响应
    public static <T> ApiResult<T> fail() {
        return of(GlobalResultCode.FAILURE);
    }

    public static <T> ApiResult<T> fail(String message) {
        return new ApiResult<T>()
                .setCode(GlobalResultCode.FAILURE.getCode())
                .setMessage(message);
    }
    
    public static <T> ApiResult<T> fail(IResultCode resultCode) {
        return of(resultCode);
    }

    public static <T> ApiResult<T> fail(IResultCode resultCode, String message) {
        return new ApiResult<T>()
                .setCode(resultCode.getCode())
                .setMessage(message);
    }
    
    // 条件响应
    public static <T> ApiResult<T> condition(boolean condition) {
        return condition ? ok() : fail();
    }
    
    public static <T> ApiResult<T> condition(boolean condition, T data) {
        return condition ? ok(data) : fail();
    }
    
    // 函数式方法
    public <R> ApiResult<R> map(Function<T, R> mapper) {
        if (isSuccess() && this.data != null) {
            return ApiResult.ok(mapper.apply(this.data));
        }
        return (ApiResult<R>) this;
    }
    
    public ApiResult<T> peek(Consumer<T> consumer) {
        if (isSuccess() && this.data != null) {
            consumer.accept(this.data);
        }
        return this;
    }
    
    // 数据获取方法
    public T getDataOrThrow() {
        if (!isSuccess()) {
            throw new RuntimeException(this.message);
        }
        return this.data;
    }
    
    public T getDataOrElse(T other) {
        return isSuccess() ? this.data : other;
    }
    
    public Optional<T> optional() {
        return Optional.ofNullable(this.data);
    }
    
    // 状态判断
    public boolean isSuccess() {
        return GlobalResultCode.SUCCESS.getCode() == this.code;
    }
    
    // 链式调用方法
    private ApiResult<T> setCode(int code) {
        this.code = code;
        return this;
    }
    
    public ApiResult<T> setMessage(String message) {
        this.message = message;
        return this;
    }
    
    public ApiResult<T> setData(T data) {
        this.data = data;
        return this;
    }
    
    public ApiResult<T> setRequestId(String requestId) {
        this.requestId = requestId;
        return this;
    }
}
