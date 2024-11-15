package com.steven.chameleon.core.executor;

import com.steven.chameleon.core.exception.ChameleonException;
import com.steven.chameleon.core.exception.ErrorCode;
import com.steven.chameleon.core.type.TypeHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * <template>desc<template>.
 *
 * @author: steven
 * @date: 2024/11/14 17:33
 */
public class DefaultResultSetHandler<T> implements ResultSetHandler<T> {

    private static final Logger logger = LoggerFactory.getLogger(DefaultResultSetHandler.class);

    private final TypeHandler typeHandler;

    public DefaultResultSetHandler(TypeHandler typeHandler) {
        this.typeHandler = typeHandler;
    }

    @Override
    public List<T> handle(ResultSet rs, Class<T> type) throws SQLException {
        List<T> results = new ArrayList<>();
        while (rs.next()) {
            results.add(handleRow(rs, type));
        }
        return results;
    }

    @Override
    public T handleSingle(ResultSet rs, Class<T> type) throws SQLException {
        return rs.next() ? handleRow(rs, type) : null;
    }

    private T handleRow(ResultSet rs, Class<T> type) throws SQLException {
        if (type.isPrimitive() || TypeHandler.isSimpleType(type)) {
            return typeHandler.convert(rs.getObject(1), type);
        }

        T instance = createInstance(type);
        ResultSetMetaData meta = rs.getMetaData();
        int cols = meta.getColumnCount();

        for (int i = 1; i <= cols; i++) {
            String columnName = meta.getColumnLabel(i);
            Object value = rs.getObject(i);
            setProperty(instance, columnName, value);
        }

        return instance;
    }

    private T createInstance(Class<T> type) {
        try {
            return type.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new ChameleonException("Failed to create instance of " + type,
                    ErrorCode.DATA_CONVERSION_ERROR, e);
        }
    }

    private void setProperty(T instance, String property, Object value) {
        try {
            Field field = instance.getClass().getDeclaredField(property);
            field.setAccessible(true);
            field.set(instance, typeHandler.convert(value, field.getType()));
        } catch (Exception e) {
            // 记录错误但继续处理其他字段
            logger.warn("Failed to set property: " + property, e);
        }
    }
}