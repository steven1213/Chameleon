package com.steven.chameleon.core.executor;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * <template>desc<template>.
 *
 * @author: steven
 * @date: 2024/11/15 09:02
 */
public interface SqlExecutor {
    /**
     * 执行查询
     */
    <T> List<T> query(Connection conn, String sql, ResultSetHandler<T> handler,
                      Class<T> type, Object... params) throws SQLException;

    /**
     * 执行更新
     */
    int update(Connection conn, String sql, Object... params) throws SQLException;

    /**
     * 执行批量更新
     */
    int[] batch(Connection conn, String sql, List<Object[]> paramsList) throws SQLException;
}
