package com.steven.chameleon.core.annotation;

import java.lang.annotation.*;

/**
 * <template>字段验证注解<template>.
 *
 * @author: steven
 * @date: 2024/11/15 11:40
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Validate {
    /**
     * 是否必填
     */
    boolean required() default false;

    /**
     * 最小长度
     */
    int minLength() default 0;

    /**
     * 最大长度
     */
    int maxLength() default Integer.MAX_VALUE;

    /**
     * 正则表达式
     */
    String pattern() default "";

    /**
     * 验证消息
     */
    String message() default "";
}

