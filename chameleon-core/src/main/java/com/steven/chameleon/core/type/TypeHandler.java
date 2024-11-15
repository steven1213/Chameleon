package com.steven.chameleon.core.type;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * <template>desc<template>.
 *
 * @author: steven
 * @date: 2024/11/14 17:35
 */
public interface TypeHandler {
    /**
     * 类型转换
     */
    <T> T convert(Object value, Class<T> targetType);

    /**
     * 判断是否为简单类型
     */
    static boolean isSimpleType(Class<?> type) {
        return SIMPLE_TYPES.contains(type);
    }

    /**
     * 简单类型集合
     */
    Set<Class<?>> SIMPLE_TYPES = new HashSet<Class<?>>() {{
        add(String.class);
        add(Boolean.class);
        add(Character.class);
        add(Byte.class);
        add(Short.class);
        add(Integer.class);
        add(Long.class);
        add(Float.class);
        add(Double.class);
        add(BigDecimal.class);
        add(Date.class);
        add(java.sql.Date.class);
        add(Timestamp.class);
        add(LocalDate.class);
        add(LocalDateTime.class);
        // 原始类型
        add(boolean.class);
        add(char.class);
        add(byte.class);
        add(short.class);
        add(int.class);
        add(long.class);
        add(float.class);
        add(double.class);
    }};
}
