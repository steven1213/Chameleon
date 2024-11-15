package com.steven.chameleon.core.annotation;

import java.lang.annotation.*;

/**
 * <template>外键关联注解<template>.
 *
 * @author: steven
 * @date: 2024/11/15 11:40
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ForeignKey {
    /**
     * 关联表名
     */
    String table();

    /**
     * 关联字段
     */
    String field();

    /**
     * 级联操作
     */
    CascadeType[] cascade() default {};
}

