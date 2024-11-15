package com.steven.chameleon.core.annotation;

import java.lang.annotation.*;

/**
 * <template>字段序列化注解<template>.
 *
 * @author: steven
 * @date: 2024/11/15 11:40
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Serialized {
    /**
     * 序列化类型
     */
    SerializeType value() default SerializeType.JSON;
}

