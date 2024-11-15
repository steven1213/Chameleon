package com.steven.chameleon.core.executor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


/**
 * <template>desc<template>.
 *
 * @author: steven
 * @date: 2024/11/15 09:04
 */
public class DefaultSqlExecutor implements SqlExecutor {
    private static final Logger logger = LoggerFactory.getLogger(DefaultSqlExecutor.class);

    private final StatementHandler statementHandler;

    public DefaultSqlExecutor() {
        this.statementHandler = new DefaultStatementHandler();
    }

    @Override
    public <T> List<T> query(Connection conn, String sql, ResultSetHandler<T> handler,
                             Class<T> type, Object... params) throws SQLException {
        logger.debug("Executing query: {}", sql);

        try (PreparedStatement ps = statementHandler.prepare(conn, sql, params);
             ResultSet rs = ps.executeQuery()) {
            return handler.handle(rs, type);
        } catch (SQLException e) {
            throw new SQLException("Error executing query: " + sql, e);
        }
    }


    @Override
    public int update(Connection conn, String sql, Object... params) throws SQLException {
        logger.debug("Executing update: {}", sql);

        try (PreparedStatement ps = statementHandler.prepare(conn, sql, params)) {
            return ps.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Error executing update: " + sql, e);
        }
    }

    @Override
    public int[] batch(Connection conn, String sql, List<Object[]> paramsList)
            throws SQLException {
        logger.debug("Executing batch update: {}", sql);

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            for (Object[] params : paramsList) {
                statementHandler.setParameters(ps, params);
                ps.addBatch();
            }
            return ps.executeBatch();
        } catch (SQLException e) {
            throw new SQLException("Error executing batch update: " + sql, e);
        }
    }
}
