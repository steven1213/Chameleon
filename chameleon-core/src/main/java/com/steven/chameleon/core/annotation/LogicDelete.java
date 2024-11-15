package com.steven.chameleon.core.annotation;

import java.lang.annotation.*;

/**
 * <template>逻辑删除注解<template>.
 *
 * @author: steven
 * @date: 2024/11/15 11:40
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogicDelete {
    /**
     * 删除标记的值
     */
    String value() default "1";

    /**
     * 未删除标记的值
     */
    String notValue() default "0";
}

