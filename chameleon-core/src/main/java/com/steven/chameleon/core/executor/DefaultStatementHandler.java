package com.steven.chameleon.core.executor;

import com.steven.chameleon.core.exception.ChameleonException;
import com.steven.chameleon.core.exception.ErrorCode;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * <template>desc<template>.
 *
 * @author: steven
 * @date: 2024/11/14 17:32
 */
public class DefaultStatementHandler implements StatementHandler {

    @Override
    public PreparedStatement prepare(Connection connection, String sql, Object... params)
            throws SQLException {
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            setParameters(ps, params);
            return ps;
        } catch (SQLException e) {
            throw new ChameleonException("Failed to prepare statement",
                    ErrorCode.SQL_ERROR, e);
        }
    }

    @Override
    public void setParameters(PreparedStatement ps, Object... params) throws SQLException {
        if (params != null) {
            for (int i = 0; i < params.length; i++) {
                setParameter(ps, i + 1, params[i]);
            }
        }
    }

    /**
     * 设置单个参数
     */
    protected void setParameter(PreparedStatement ps, int index, Object param)
            throws SQLException {
        if (param == null) {
            ps.setNull(index, java.sql.Types.OTHER);
            return;
        }

        // 根据参数类型设置对应的值
        if (param instanceof String) {
            ps.setString(index, (String) param);
        } else if (param instanceof Integer) {
            ps.setInt(index, (Integer) param);
        } else if (param instanceof Long) {
            ps.setLong(index, (Long) param);
        } else if (param instanceof Double) {
            ps.setDouble(index, (Double) param);
        } else if (param instanceof Float) {
            ps.setFloat(index, (Float) param);
        } else if (param instanceof Boolean) {
            ps.setBoolean(index, (Boolean) param);
        } else if (param instanceof java.sql.Date) {
            ps.setDate(index, (java.sql.Date) param);
        } else if (param instanceof java.sql.Time) {
            ps.setTime(index, (java.sql.Time) param);
        } else if (param instanceof java.sql.Timestamp) {
            ps.setTimestamp(index, (java.sql.Timestamp) param);
        } else if (param instanceof byte[]) {
            ps.setBytes(index, (byte[]) param);
        } else {
            // 对于其他类型，尝试使用toString()方法
            ps.setString(index, param.toString());
        }
    }
}