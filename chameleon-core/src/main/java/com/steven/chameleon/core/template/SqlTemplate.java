package com.steven.chameleon.core.template;

import com.steven.chameleon.core.exception.ChameleonException;
import com.steven.chameleon.core.exception.ErrorCode;
import com.steven.chameleon.core.executor.ResultSetHandler;
import com.steven.chameleon.core.executor.SqlExecutor;
import com.steven.chameleon.core.transaction.TransactionManager;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * <template>desc<template>.
 *
 * @author: steven
 * @date: 2024/11/15 09:05
 */
public class SqlTemplate {
    private final DataSource dataSource;
    private final SqlExecutor sqlExecutor;
    private final TransactionManager transactionManager;

    public SqlTemplate(DataSource dataSource, SqlExecutor sqlExecutor,
                       TransactionManager transactionManager) {
        this.dataSource = dataSource;
        this.sqlExecutor = sqlExecutor;
        this.transactionManager = transactionManager;
    }

    /**
     * 执行查询
     */
    public <T> List<T> query(String sql, ResultSetHandler<T> handler, Class<T> type, Object... params) {
        try (Connection conn = getConnection()) {
            return sqlExecutor.query(conn, sql, handler, type, params);
        } catch (SQLException e) {
            throw new ChameleonException("Error executing query",
                    ErrorCode.SQL_EXECUTION_ERROR, e);
        }
    }

    /**
     * 执行更新
     */
    public int update(String sql, Object... params) {
        try (Connection conn = getConnection()) {
            return sqlExecutor.update(conn, sql, params);
        } catch (SQLException e) {
            throw new ChameleonException("Error executing update",
                    ErrorCode.SQL_EXECUTION_ERROR, e);
        }
    }

    /**
     * 执行批量更新
     */
    public int[] batch(String sql, List<Object[]> paramsList) {
        try (Connection conn = getConnection()) {
            return sqlExecutor.batch(conn, sql, paramsList);
        } catch (SQLException e) {
            throw new ChameleonException("Error executing batch update",
                    ErrorCode.SQL_EXECUTION_ERROR, e);
        }
    }

    /**
     * 在事务中执行
     */
    public <T> T executeInTransaction(SqlCallback<T> action) {
        try {
            transactionManager.beginTransaction();
            T result = action.execute();
            transactionManager.commit();
            return result;
        } catch (Exception e) {
            transactionManager.rollback();
            throw new ChameleonException("Error executing in transaction",
                    ErrorCode.TRANSACTION_ERROR, e);
        }
    }

    private Connection getConnection() throws SQLException {
        if (transactionManager.isTransactionActive()) {
            return transactionManager.getCurrentTransaction().getConnection();
        }
        return dataSource.getConnection();
    }
}

