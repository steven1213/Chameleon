package com.steven.chameleon.core.connection;

import com.steven.chameleon.core.config.DatabaseConfig;
import com.steven.chameleon.core.exception.ChameleonException;
import com.steven.chameleon.core.exception.ErrorCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * <template>desc<template>.
 *
 * @author: steven
 * @date: 2024/11/14 16:58
 */
public class DefaultConnectionPool implements ConnectionPool {
    private static final Logger logger = LoggerFactory.getLogger(DefaultConnectionPool.class);

    private final DatabaseConfig config;
    private final BlockingQueue<PooledConnection> idleConnections;
    private final AtomicInteger totalConnections = new AtomicInteger(0);
    private final AtomicInteger activeConnections = new AtomicInteger(0);
    private volatile boolean isShutdown = false;

    public DefaultConnectionPool(DatabaseConfig config) {
        this.config = config;
        this.idleConnections = new ArrayBlockingQueue<>(config.getPoolConfig().getMaxPoolSize());
        initializePool();
    }

    private void initializePool() {
        try {
            // 加载驱动
            Class.forName(config.getDriverClassName());

            // 初始化最小连接数
            for (int i = 0; i < config.getPoolConfig().getMinPoolSize(); i++) {
                idleConnections.offer(createConnection());
                totalConnections.incrementAndGet();
            }
        } catch (Exception e) {
            throw new ChameleonException("Failed to initialize connection pool",
                    ErrorCode.CONNECTION_ERROR, e);
        }
    }

    private PooledConnection createConnection() throws Exception {
        Connection conn = DriverManager.getConnection(
                config.getUrl(),
                config.getUsername(),
                config.getPassword()
        );
        // 这里传入 this 是安全的，因为类已经实现了 ConnectionPool 接口
        return new PooledConnection(conn, this);
    }

    @Override
    public Connection acquire() throws ChameleonException {
        if (isShutdown) {
            throw new ChameleonException("Connection pool is shutdown",
                    ErrorCode.CONNECTION_ERROR);
        }

        try {
            PooledConnection conn = idleConnections.poll(
                    config.getPoolConfig().getConnectionTimeout(),
                    TimeUnit.MILLISECONDS
            );

            if (conn == null) {
                // 如果没有空闲连接且未达到最大连接数，创建新连接
                if (totalConnections.get() < config.getPoolConfig().getMaxPoolSize()) {
                    conn = createConnection();
                    totalConnections.incrementAndGet();
                } else {
                    throw new ChameleonException("Connection pool is full",
                            ErrorCode.CONNECTION_ERROR);
                }
            }

            if (conn != null) {
                activeConnections.incrementAndGet();
            }

            return conn;
        } catch (Exception e) {
            throw new ChameleonException("Failed to acquire connection",
                    ErrorCode.CONNECTION_ERROR, e);
        }
    }

    @Override
    public void release(Connection connection) throws ChameleonException {
        if (!(connection instanceof PooledConnection)) {
            throw new ChameleonException("Invalid connection object",
                    ErrorCode.CONNECTION_ERROR);
        }

        PooledConnection pooledConnection = (PooledConnection) connection;
        try {
            if (!pooledConnection.isClosed()) {
                if (!idleConnections.offer(pooledConnection)) {
                    pooledConnection.reallyClose();
                    totalConnections.decrementAndGet();
                }
                activeConnections.decrementAndGet();
            } else {
                totalConnections.decrementAndGet();
            }
        } catch (Exception e) {
            throw new ChameleonException("Failed to release connection",
                    ErrorCode.CONNECTION_ERROR, e);
        }
    }

    @Override
    public void shutdown() throws ChameleonException {
        isShutdown = true;
        try {
            // 关闭所有空闲连接
            for (PooledConnection conn : idleConnections) {
                conn.reallyClose();
            }
            idleConnections.clear();
            totalConnections.set(0);
            activeConnections.set(0);
        } catch (Exception e) {
            throw new ChameleonException("Failed to shutdown connection pool",
                    ErrorCode.CONNECTION_ERROR, e);
        }
    }

    @Override
    public PoolStatus getStatus() {
        return new PoolStatus(
                activeConnections.get(),
                idleConnections.size(),
                totalConnections.get()
        );
    }

    /**
     * 验证连接是否有效
     */
    public boolean validateConnection(Connection connection) {
        if (connection == null) {
            return false;
        }

        try {
            return !connection.isClosed() &&
                    connection.isValid(config.getPoolConfig().getConnectionTimeout());
        } catch (Exception e) {
            logger.warn("Failed to validate connection", e);
            return false;
        }
    }

    /**
     * 获取连接池配置
     */
    public DatabaseConfig getConfig() {
        return config;
    }

    /**
     * 判断连接池是否已关闭
     */
    public boolean isShutdown() {
        return isShutdown;
    }
}
