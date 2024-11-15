package com.steven.chameleon.core.exception;

/**
 * <template>Chameleon框架统一异常类<template>.
 *
 * @author: steven
 * @date: 2024/11/14 16:42
 */
public class ChameleonException extends RuntimeException {

    private final ErrorCode errorCode;
    private final String errorMessage;
    private final Object[] parameters;

    /**
     * 创建一个基础异常
     */
    public ChameleonException(String message) {
        super(message);
        this.errorCode = ErrorCode.UNKNOWN;
        this.errorMessage = message;
        this.parameters = new Object[0];
    }

    /**
     * 创建一个带错误码的异常
     */
    public ChameleonException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
        this.errorMessage = message;
        this.parameters = new Object[0];
    }

    /**
     * 创建一个带错误码和原始异常的异常
     */
    public ChameleonException(String message, ErrorCode errorCode, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
        this.errorMessage = message;
        this.parameters = new Object[0];
    }

    /**
     * 创建一个带错误码和参数的异常
     */
    public ChameleonException(String message, ErrorCode errorCode, Object... parameters) {
        super(message);
        this.errorCode = errorCode;
        this.errorMessage = message;
        this.parameters = parameters;
    }

    /**
     * 创建一个完整的异常
     */
    public ChameleonException(String message, ErrorCode errorCode, Throwable cause, Object... parameters) {
        super(message, cause);
        this.errorCode = errorCode;
        this.errorMessage = message;
        this.parameters = parameters;
    }

    /**
     * 获取错误码
     */
    public ErrorCode getErrorCode() {
        return errorCode;
    }

    /**
     * 获取错误消息
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * 获取错误参数
     */
    public Object[] getParameters() {
        return parameters;
    }

    /**
     * 判断是否为特定错误类型
     */
    public boolean is(ErrorCode code) {
        return this.errorCode == code;
    }

    /**
     * 判断是否为系统错误
     */
    public boolean isSystemError() {
        return errorCode.isSystemError();
    }

    /**
     * 判断是否为配置错误
     */
    public boolean isConfigError() {
        return errorCode.isConfigError();
    }

    /**
     * 判断是否为连接错误
     */
    public boolean isConnectionError() {
        return errorCode.isConnectionError();
    }

    /**
     * 判断是否为SQL错误
     */
    public boolean isSqlError() {
        return errorCode.isSqlError();
    }

    @Override
    public String getMessage() {
        StringBuilder sb = new StringBuilder();
        sb.append(errorCode.toString());
        if (errorMessage != null && !errorMessage.isEmpty()) {
            sb.append(": ").append(errorMessage);
        }
        if (parameters != null && parameters.length > 0) {
            sb.append(" [Parameters: ");
            for (int i = 0; i < parameters.length; i++) {
                if (i > 0) {
                    sb.append(", ");
                }
                sb.append(parameters[i]);
            }
            sb.append("]");
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        String s = getClass().getName() + ": " + getMessage();
        Throwable cause = getCause();
        if (cause != null) {
            s += "; nested exception is " + cause.toString();
        }
        return s;
    }

    /**
     * 创建一个新的异常，但保持原有的堆栈跟踪
     */
    public ChameleonException withNewMessage(String newMessage) {
        ChameleonException newException = new ChameleonException(newMessage, this.errorCode, this.getCause(), this.parameters);
        newException.setStackTrace(this.getStackTrace());
        return newException;
    }

    /**
     * 包装其他异常
     */
    public static ChameleonException wrap(Throwable throwable, ErrorCode errorCode) {
        if (throwable instanceof ChameleonException) {
            return (ChameleonException) throwable;
        }
        return new ChameleonException(throwable.getMessage(), errorCode, throwable);
    }

    /**
     * 创建一个新的异常
     */
    public static ChameleonException of(String message, ErrorCode errorCode) {
        return new ChameleonException(message, errorCode);
    }

    /**
     * 创建一个带参数的新异常
     */
    public static ChameleonException of(String message, ErrorCode errorCode, Object... parameters) {
        return new ChameleonException(message, errorCode, parameters);
    }
}