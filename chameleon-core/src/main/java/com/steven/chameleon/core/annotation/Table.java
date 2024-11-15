package com.steven.chameleon.core.annotation;

import java.lang.annotation.*;

/**
 * <template>表名注解<template>.
 *
 * @author: steven
 * @date: 2024/11/15 11:40
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Table {
    /**
     * 表名
     */
    String value() default "";

    /**
     * 表描述
     */
    String description() default "";
}

