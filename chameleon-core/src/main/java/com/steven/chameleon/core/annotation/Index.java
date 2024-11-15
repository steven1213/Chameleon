package com.steven.chameleon.core.annotation;

import java.lang.annotation.*;

/**
 * <template>单个索引注解<template>.
 *
 * @author: steven
 * @date: 2024/11/15 11:40
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Repeatable(Indexes.class)
public @interface Index {
    /**
     * 索引名称
     */
    String name();

    /**
     * 索引字段
     */
    String[] columns();

    /**
     * 是否唯一索引
     */
    boolean unique() default false;
}

