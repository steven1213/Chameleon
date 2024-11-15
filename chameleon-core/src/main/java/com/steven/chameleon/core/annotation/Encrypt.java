package com.steven.chameleon.core.annotation;

import java.lang.annotation.*;

/**
 * <template>字段加密注解<template>.
 *
 * @author: steven
 * @date: 2024/11/15 11:40
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Encrypt {
    /**
     * 加密算法
     */
    EncryptType value() default EncryptType.AES;

    /**
     * 密钥
     */
    String key() default "";
}

