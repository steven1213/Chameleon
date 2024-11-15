package com.steven.chameleon.core.annotation;

import java.lang.annotation.*;

/**
 * <template>枚举类型注解<template>.
 *
 * @author: steven
 * @date: 2024/11/15 11:40
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EnumValue {
    /**
     * 枚举值的属性名
     */
    String value() default "value";
}

