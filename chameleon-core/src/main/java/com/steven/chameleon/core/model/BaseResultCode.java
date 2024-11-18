package com.steven.chameleon.core.model;

public abstract class BaseResultCode implements IResultCode {
    private final int code;
    private final String message;

    protected BaseResultCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public int getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}