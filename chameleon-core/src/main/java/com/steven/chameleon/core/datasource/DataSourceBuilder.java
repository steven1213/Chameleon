package com.steven.chameleon.core.datasource;

import com.steven.chameleon.core.config.DatabaseConfig;
import com.steven.chameleon.core.connection.ConnectionPool;
import com.steven.chameleon.core.exception.ChameleonException;
import com.steven.chameleon.core.exception.ErrorCode;

/**
 * <template>desc<template>.
 *
 * @author: steven
 * @date: 2024/11/15 08:57
 */
public class DataSourceBuilder {
    private DatabaseConfig config;
    private ConnectionPool connectionPool;

    public DataSourceBuilder config(DatabaseConfig config) {
        this.config = config;
        return this;
    }

    public DataSourceBuilder connectionPool(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
        return this;
    }

    public ChameleonDataSource build() {
        validate();
        return new ChameleonDataSource(config, connectionPool);
    }

    private void validate() {
        if (config == null) {
            throw new ChameleonException("Database configuration is required",
                    ErrorCode.CONFIG_MISSING);
        }
        if (connectionPool == null) {
            throw new ChameleonException("Connection pool is required",
                    ErrorCode.CONFIG_MISSING);
        }
    }
}
