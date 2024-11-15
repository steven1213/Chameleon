package com.steven.chameleon.core.config;

/**
 * <template>desc<template>.
 *
 * @author: steven
 * @date: 2024/11/14 17:30
 */
public /**
 * 默认连接池配置实现
 */
class DefaultPoolConfig implements PoolConfig {
    private static final int DEFAULT_MIN_POOL_SIZE = 5;
    private static final int DEFAULT_MAX_POOL_SIZE = 20;
    private static final int DEFAULT_CONNECTION_TIMEOUT = 30000;
    private static final int DEFAULT_IDLE_TIMEOUT = 600000;

    private final int minPoolSize;
    private final int maxPoolSize;
    private final int connectionTimeout;
    private final long idleTimeout;

    public DefaultPoolConfig() {
        this(DEFAULT_MIN_POOL_SIZE, DEFAULT_MAX_POOL_SIZE,
                DEFAULT_CONNECTION_TIMEOUT, DEFAULT_IDLE_TIMEOUT);
    }

    public DefaultPoolConfig(int minPoolSize, int maxPoolSize,
                             int connectionTimeout, int idleTimeout) {
        this.minPoolSize = validateMinPoolSize(minPoolSize);
        this.maxPoolSize = validateMaxPoolSize(maxPoolSize, minPoolSize);
        this.connectionTimeout = validateTimeout(connectionTimeout, "Connection");
        this.idleTimeout = validateTimeout(idleTimeout, "Idle");
    }

    @Override
    public int getMinPoolSize() {
        return minPoolSize;
    }

    @Override
    public int getMaxPoolSize() {
        return maxPoolSize;
    }

    @Override
    public int getConnectionTimeout() {
        return connectionTimeout;
    }

    @Override
    public long getIdleTimeout() {
        return idleTimeout;
    }

    private int validateMinPoolSize(int minPoolSize) {
        if (minPoolSize < 0) {
            throw new IllegalArgumentException("Min pool size cannot be negative");
        }
        return minPoolSize;
    }

    private int validateMaxPoolSize(int maxPoolSize, int minPoolSize) {
        if (maxPoolSize < minPoolSize) {
            throw new IllegalArgumentException(
                    "Max pool size cannot be less than min pool size");
        }
        return maxPoolSize;
    }

    private int validateTimeout(int timeout, String type) {
        if (timeout < 0) {
            throw new IllegalArgumentException(
                    type + " timeout cannot be negative");
        }
        return timeout;
    }

    @Override
    public String toString() {
        return "DefaultPoolConfig{" +
                "minPoolSize=" + minPoolSize +
                ", maxPoolSize=" + maxPoolSize +
                ", connectionTimeout=" + connectionTimeout +
                ", idleTimeout=" + idleTimeout +
                '}';
    }
}