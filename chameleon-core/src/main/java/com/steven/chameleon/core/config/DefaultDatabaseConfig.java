package com.steven.chameleon.core.config;

import java.util.Properties;

/**
 * <template>desc<template>.
 *
 * @author: steven
 * @date: 2024/11/14 17:19
 */
public class DefaultDatabaseConfig implements DatabaseConfig {
    private final String url;
    private final String username;
    private final String password;
    private final String driverClassName;
    private final Properties properties;
    private final PoolConfig poolConfig;

    public DefaultDatabaseConfig(String url,
                                 String username,
                                 String password,
                                 String driverClassName,
                                 Properties properties,
                                 PoolConfig poolConfig) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.driverClassName = driverClassName;
        this.properties = properties != null ? properties : new Properties();
        this.poolConfig = poolConfig != null ? poolConfig : new DefaultPoolConfig();
    }

    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getDriverClassName() {
        return driverClassName;
    }

    @Override
    public PoolConfig getPoolConfig() {
        return poolConfig;
    }

    @Override
    public Properties getProperties() {
        return new Properties(properties);
    }

    /**
     * 获取特定属性值
     */
    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    /**
     * 获取特定属性值，如果不存在则返回默认值
     */
    public String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }

    @Override
    public String toString() {
        return "DefaultDatabaseConfig{" +
                "url='" + url + '\'' +
                ", username='" + username + '\'' +
                ", driverClassName='" + driverClassName + '\'' +
                ", poolConfig=" + poolConfig +
                '}';
    }
}
