package com.steven.chameleon.core.annotation;

import com.steven.chameleon.core.converter.AttributeConverter;

import java.lang.annotation.*;
/**
 * <template>字段转换注解<template>.
 *
 * @author: steven
 * @date: 2024/11/15 11:40
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Convert {
    /**
     * 转换器类
     */
    Class<? extends AttributeConverter<?, ?>> converter();
}

