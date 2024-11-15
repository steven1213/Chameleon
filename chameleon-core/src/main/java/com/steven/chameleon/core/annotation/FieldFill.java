package com.steven.chameleon.core.annotation;

import java.lang.annotation.*;

/**
 * <template>字段填充注解<template>.
 *
 * @author: steven
 * @date: 2024/11/15 11:40
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FieldFill {
    /**
     * 填充策略
     */
    FillStrategy value();
}
