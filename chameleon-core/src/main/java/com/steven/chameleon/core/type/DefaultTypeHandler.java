package com.steven.chameleon.core.type;

/**
 * <template>desc<template>.
 *
 * @author: steven
 * @date: 2024/11/14 17:35
 */

import com.steven.chameleon.core.exception.ChameleonException;
import com.steven.chameleon.core.exception.ErrorCode;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * 默认类型处理器实现
 */
public class DefaultTypeHandler implements TypeHandler {

    @Override
    @SuppressWarnings("unchecked")
    public <T> T convert(Object value, Class<T> type) {
        if (value == null) {
            return null;
        }

        try {
            // 如果值已经是目标类型
            if (type.isInstance(value)) {
                return (T) value;
            }

            // 处理原始类型
            if (type.isPrimitive()) {
                return handlePrimitive(value, type);
            }

            // 处理数字类型转换
            if (Number.class.isAssignableFrom(type)) {
                return handleNumber(value, type);
            }

            // 处理日期类型转换
            if (isDateType(type)) {
                return handleDate(value, type);
            }

            // 处理字符串转换
            if (type == String.class) {
                return (T) value.toString();
            }

            // 处理布尔值转换
            if (type == Boolean.class || type == boolean.class) {
                return (T) handleBoolean(value);
            }

            throw new ChameleonException(
                    "Unsupported type conversion from " + value.getClass() + " to " + type,
                    ErrorCode.DATA_TYPE_ERROR
            );

        } catch (Exception e) {
            throw new ChameleonException(
                    "Failed to convert " + value + " to type " + type,
                    ErrorCode.DATA_CONVERSION_ERROR,
                    e
            );
        }
    }

    @SuppressWarnings("unchecked")
    private <T> T handlePrimitive(Object value, Class<T> type) {
        if (type == int.class) {
            return (T) Integer.valueOf(((Number) value).intValue());
        }
        if (type == long.class) {
            return (T) Long.valueOf(((Number) value).longValue());
        }
        if (type == double.class) {
            return (T) Double.valueOf(((Number) value).doubleValue());
        }
        if (type == float.class) {
            return (T) Float.valueOf(((Number) value).floatValue());
        }
        if (type == short.class) {
            return (T) Short.valueOf(((Number) value).shortValue());
        }
        if (type == byte.class) {
            return (T) Byte.valueOf(((Number) value).byteValue());
        }
        if (type == boolean.class) {
            return (T) handleBoolean(value);
        }
        throw new ChameleonException(
                "Unsupported primitive type: " + type,
                ErrorCode.DATA_TYPE_ERROR
        );
    }

    @SuppressWarnings("unchecked")
    private <T> T handleNumber(Object value, Class<T> type) {
        if (value instanceof Number) {
            Number number = (Number) value;
            if (type == Integer.class) {
                return (T) Integer.valueOf(number.intValue());
            }
            if (type == Long.class) {
                return (T) Long.valueOf(number.longValue());
            }
            if (type == Double.class) {
                return (T) Double.valueOf(number.doubleValue());
            }
            if (type == Float.class) {
                return (T) Float.valueOf(number.floatValue());
            }
            if (type == Short.class) {
                return (T) Short.valueOf(number.shortValue());
            }
            if (type == Byte.class) {
                return (T) Byte.valueOf(number.byteValue());
            }
            if (type == BigDecimal.class) {
                return (T) new BigDecimal(number.toString());
            }
        }
        throw new ChameleonException(
                "Cannot convert " + value + " to " + type,
                ErrorCode.DATA_TYPE_ERROR
        );
    }

    @SuppressWarnings("unchecked")
    private <T> T handleDate(Object value, Class<T> type) {
        if (value instanceof Date) {
            Date date = (Date) value;
            if (type == LocalDateTime.class) {
                return (T) new Timestamp(date.getTime()).toLocalDateTime();
            }
            if (type == LocalDate.class) {
                return (T) new java.sql.Date(date.getTime()).toLocalDate();
            }
            if (type == Timestamp.class) {
                return (T) new Timestamp(date.getTime());
            }
            if (type == java.sql.Date.class) {
                return (T) new java.sql.Date(date.getTime());
            }
        }
        throw new ChameleonException(
                "Cannot convert " + value + " to " + type,
                ErrorCode.DATA_TYPE_ERROR
        );
    }

    private Boolean handleBoolean(Object value) {
        if (value instanceof Boolean) {
            return (Boolean) value;
        }
        if (value instanceof Number) {
            return ((Number) value).intValue() != 0;
        }
        if (value instanceof String) {
            String strVal = ((String) value).toLowerCase();
            return "true".equals(strVal) || "1".equals(strVal) || "yes".equals(strVal);
        }
        throw new ChameleonException(
                "Cannot convert " + value + " to boolean",
                ErrorCode.DATA_TYPE_ERROR
        );
    }

    private boolean isDateType(Class<?> type) {
        return type == Date.class ||
                type == java.sql.Date.class ||
                type == Timestamp.class ||
                type == LocalDate.class ||
                type == LocalDateTime.class;
    }
}