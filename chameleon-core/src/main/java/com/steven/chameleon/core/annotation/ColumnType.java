package com.steven.chameleon.core.annotation;

import java.lang.annotation.*;

/**
 * <template>数据库字段类型注解<template>.
 *
 * @author: steven
 * @date: 2024/11/15 11:40
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ColumnType {
    /**
     * 数据库字段类型
     */
    String value();

    /**
     * 精度(用于decimal)
     */
    int precision() default 0;

    /**
     * 小数位数(用于decimal)
     */
    int scale() default 0;
}

