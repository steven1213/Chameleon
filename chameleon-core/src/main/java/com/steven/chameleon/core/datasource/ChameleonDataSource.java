package com.steven.chameleon.core.datasource;

import com.steven.chameleon.core.config.DatabaseConfig;
import com.steven.chameleon.core.connection.ConnectionPool;
import com.steven.chameleon.core.exception.ChameleonException;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

/**
 * <template>Chameleon数据源实现<template>.
 *
 * @author: steven
 * @date: 2024/11/15 08:56
 */
public class ChameleonDataSource implements DataSource {

    private final DatabaseConfig config;
    private final ConnectionPool connectionPool;
    private PrintWriter logWriter;
    private int loginTimeout;

    public ChameleonDataSource(DatabaseConfig config, ConnectionPool connectionPool) {
        this.config = config;
        this.connectionPool = connectionPool;
    }

    @Override
    public Connection getConnection() throws SQLException {
        try {
            return connectionPool.acquire();
        } catch (ChameleonException e) {
            throw new SQLException("Failed to get connection", e);
        }
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        throw new SQLFeatureNotSupportedException("Custom credentials not supported");
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        return logWriter;
    }

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {
        this.logWriter = out;
    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {
        this.loginTimeout = seconds;
    }

    @Override
    public int getLoginTimeout() throws SQLException {
        return loginTimeout;
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        throw new SQLFeatureNotSupportedException("getParentLogger not supported");
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        if (iface.isInstance(this)) {
            return (T) this;
        }
        throw new SQLException("DataSource cannot be unwrapped to " + iface.getName());
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return iface.isInstance(this);
    }

    /**
     * 获取数据源配置
     */
    public DatabaseConfig getConfig() {
        return config;
    }

    /**
     * 获取连接池
     */
    public ConnectionPool getConnectionPool() {
        return connectionPool;
    }

    /**
     * 关闭数据源
     */
    public void close() {
        try {
            connectionPool.shutdown();
        } catch (ChameleonException e) {
            // 记录关闭错误但不抛出
            if (logWriter != null) {
                e.printStackTrace(logWriter);
            }
        }
    }
}