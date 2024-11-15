package com.steven.chameleon.core.datasource;

import com.steven.chameleon.core.config.DatabaseConfig;
import com.steven.chameleon.core.connection.ConnectionPool;
import com.steven.chameleon.core.connection.DefaultConnectionPool;

import javax.sql.DataSource;

/**
 * <template>desc<template>.
 *
 * @author: steven
 * @date: 2024/11/15 08:59
 */
public class DataSourceFactory {
    /**
     * 创建默认数据源
     */
    public static DataSource createDataSource(DatabaseConfig config) {
        ConnectionPool connectionPool = new DefaultConnectionPool(config);
        return new DataSourceBuilder()
                .config(config)
                .connectionPool(connectionPool)
                .build();
    }

    /**
     * 创建自定义连接池的数据源
     */
    public static DataSource createDataSource(DatabaseConfig config, ConnectionPool connectionPool) {
        return new DataSourceBuilder()
                .config(config)
                .connectionPool(connectionPool)
                .build();
    }
}
