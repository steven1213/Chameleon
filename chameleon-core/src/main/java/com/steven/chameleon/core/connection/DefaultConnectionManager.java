package com.steven.chameleon.core.connection;

import com.steven.chameleon.core.config.DatabaseConfig;
import com.steven.chameleon.core.exception.ChameleonException;
import com.steven.chameleon.core.exception.ErrorCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;

/**
 * <template>默认连接管理器实现<template>.
 *
 * @author: steven
 * @date: 2024/11/14 16:56
 */
public class DefaultConnectionManager implements ConnectionManager {
    private static final Logger logger = LoggerFactory.getLogger(DefaultConnectionManager.class);

    private final DatabaseConfig config;
    private final AtomicReference<ConnectionPool> connectionPool;
    private final ConcurrentHashMap<String, Object> attributes;
    private volatile boolean isShutdown;

    public DefaultConnectionManager(DatabaseConfig config) {
        this.config = config;
        this.connectionPool = new AtomicReference<>(createConnectionPool());
        this.attributes = new ConcurrentHashMap<>();
        this.isShutdown = false;
    }

    private ConnectionPool createConnectionPool() {
        try {
            return new DefaultConnectionPool(config);
        } catch (Exception e) {
            logger.error("Failed to create connection pool", e);
            throw new ChameleonException("Failed to create connection pool",
                    ErrorCode.CONNECTION_ERROR, e);
        }
    }

    @Override
    public Connection getConnection() throws ChameleonException {
        checkShutdown();
        try {
            ConnectionPool pool = connectionPool.get();
            if (pool == null) {
                throw new ChameleonException("Connection pool is not initialized",
                        ErrorCode.CONNECTION_ERROR);
            }
            return pool.acquire();
        } catch (Exception e) {
            logger.error("Failed to get connection", e);
            throw new ChameleonException("Failed to get connection",
                    ErrorCode.CONNECTION_ERROR, e);
        }
    }

    @Override
    public void releaseConnection(Connection connection) throws ChameleonException {
        if (connection == null) {
            return;
        }

        try {
            if (connection instanceof PooledConnection) {
                ConnectionPool pool = connectionPool.get();
                if (pool != null) {
                    pool.release(connection);
                }
            } else {
                connection.close();
            }
        } catch (Exception e) {
            logger.error("Failed to release connection", e);
            throw new ChameleonException("Failed to release connection",
                    ErrorCode.CONNECTION_ERROR, e);
        }
    }

    @Override
    public void closeAll() throws ChameleonException {
        if (isShutdown) {
            return;
        }

        try {
            ConnectionPool pool = connectionPool.get();
            if (pool != null) {
                pool.shutdown();
            }
            isShutdown = true;
        } catch (Exception e) {
            logger.error("Failed to close all connections", e);
            throw new ChameleonException("Failed to close all connections",
                    ErrorCode.CONNECTION_ERROR, e);
        }
    }

    @Override
    public PoolStatus getPoolStatus() {
        ConnectionPool pool = connectionPool.get();
        return pool != null ? pool.getStatus() : new PoolStatus(0, 0, 0);
    }

    /**
     * 重新初始化连接池
     */
    public void reinitializePool() throws ChameleonException {
        try {
            ConnectionPool oldPool = connectionPool.get();
            if (oldPool != null) {
                oldPool.shutdown();
            }
            connectionPool.set(createConnectionPool());
            isShutdown = false;
        } catch (Exception e) {
            logger.error("Failed to reinitialize connection pool", e);
            throw new ChameleonException("Failed to reinitialize connection pool",
                    ErrorCode.CONNECTION_ERROR, e);
        }
    }

    /**
     * 设置属性
     */
    public void setAttribute(String name, Object value) {
        if (name != null) {
            if (value != null) {
                attributes.put(name, value);
            } else {
                attributes.remove(name);
            }
        }
    }

    /**
     * 获取属性
     */
    public Object getAttribute(String name) {
        return name != null ? attributes.get(name) : null;
    }

    /**
     * 检查连接管理器是否已关闭
     */
    private void checkShutdown() throws ChameleonException {
        if (isShutdown) {
            throw new ChameleonException("Connection manager is shutdown",
                    ErrorCode.CONNECTION_ERROR);
        }
    }

    /**
     * 获取当前配置
     */
    public DatabaseConfig getConfig() {
        return config;
    }

    /**
     * 判断是否已关闭
     */
    public boolean isShutdown() {
        return isShutdown;
    }

    /**
     * 获取连接池
     */
    protected ConnectionPool getConnectionPool() {
        return connectionPool.get();
    }

    /**
     * 验证连接有效性
     */
    public boolean validateConnection(Connection connection) {
        if (connection == null) {
            return false;
        }

        try {
            return !connection.isClosed() && connection.isValid(config.getPoolConfig().getConnectionTimeout());
        } catch (Exception e) {
            logger.warn("Failed to validate connection", e);
            return false;
        }
    }
}