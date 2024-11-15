package com.steven.chameleon.core.annotation;

import java.lang.annotation.*;
/**
 * <template>列名注解<template>.
 *
 * @author: steven
 * @date: 2024/11/15 11:40
 */



@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Column {
    /**
     * 列名
     */
    String value() default "";

    /**
     * 列描述
     */
    String description() default "";

    /**
     * 是否为主键
     */
    boolean isPrimary() default false;

    /**
     * 是否自增
     */
    boolean isAutoIncrement() default false;

    /**
     * 长度
     */
    int length() default 0;

    /**
     * 是否可为空
     */
    boolean nullable() default true;
}

