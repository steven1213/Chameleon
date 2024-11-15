package com.steven.chameleon.core.datasource;

import com.steven.chameleon.core.exception.ChameleonException;
import com.steven.chameleon.core.exception.ErrorCode;

import javax.sql.DataSource;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <template>desc<template>.
 *
 * @author: steven
 * @date: 2024/11/15 08:58
 */
public class DataSourceManager {
    private static final DataSourceManager INSTANCE = new DataSourceManager();
    private final Map<String, DataSource> dataSources = new ConcurrentHashMap<>();

    private DataSourceManager() {}

    public static DataSourceManager getInstance() {
        return INSTANCE;
    }

    /**
     * 注册数据源
     */
    public void registerDataSource(String name, DataSource dataSource) {
        if (name == null || name.trim().isEmpty()) {
            throw new ChameleonException("DataSource name cannot be empty",
                    ErrorCode.CONFIG_INVALID);
        }
        if (dataSource == null) {
            throw new ChameleonException("DataSource cannot be null",
                    ErrorCode.CONFIG_INVALID);
        }
        dataSources.put(name, dataSource);
    }

    /**
     * 获取数据源
     */
    public DataSource getDataSource(String name) {
        DataSource dataSource = dataSources.get(name);
        if (dataSource == null) {
            throw new ChameleonException("DataSource not found: " + name,
                    ErrorCode.CONFIG_MISSING);
        }
        return dataSource;
    }

    /**
     * 移除数据源
     */
    public void removeDataSource(String name) {
        DataSource dataSource = dataSources.remove(name);
        if (dataSource instanceof ChameleonDataSource) {
            ((ChameleonDataSource) dataSource).close();
        }
    }

    /**
     * 关闭所有数据源
     */
    public void shutdown() {
        dataSources.forEach((name, dataSource) -> {
            if (dataSource instanceof ChameleonDataSource) {
                ((ChameleonDataSource) dataSource).close();
            }
        });
        dataSources.clear();
    }

    /**
     * 获取所有数据源名称
     */
    public Set<String> getDataSourceNames() {
        return new HashSet<>(dataSources.keySet());
    }

    /**
     * 判断数据源是否存在
     */
    public boolean hasDataSource(String name) {
        return dataSources.containsKey(name);
    }

    /**
     * 获取数据源数量
     */
    public int getDataSourceCount() {
        return dataSources.size();
    }
}
