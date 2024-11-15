package com.steven.chameleon.core.annotation;

import java.lang.annotation.*;

/**
 * <template>敏感数据注解<template>.
 *
 * @author: steven
 * @date: 2024/11/15 11:40
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Sensitive {
    /**
     * 脱敏策略
     */
    SensitiveStrategy strategy();

    /**
     * 自定义脱敏规则（正则表达式）
     */
    String pattern() default "";
}

