package com.steven.chameleon.core.config;

import java.util.Properties;

/**
 * <template>desc<template>.
 *
 * @author: steven
 * @date: 2024/11/14 16:54
 */
public class DatabaseConfigBuilder {
    private String url;
    private String username;
    private String password;
    private String driverClassName;
    private Properties properties = new Properties();
    private PoolConfig poolConfig;

    public DatabaseConfigBuilder url(String url) {
        this.url = url;
        return this;
    }

    public DatabaseConfigBuilder username(String username) {
        this.username = username;
        return this;
    }

    public DatabaseConfigBuilder password(String password) {
        this.password = password;
        return this;
    }

    public DatabaseConfigBuilder driverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
        return this;
    }

    public DatabaseConfigBuilder property(String key, String value) {
        this.properties.setProperty(key, value);
        return this;
    }

    public DatabaseConfigBuilder properties(Properties properties) {
        if (properties != null) {
            this.properties.putAll(properties);
        }
        return this;
    }

    public DatabaseConfigBuilder poolConfig(PoolConfig poolConfig) {
        this.poolConfig = poolConfig;
        return this;
    }

    public DatabaseConfig build() {
        validate();
        return new DefaultDatabaseConfig(url, username, password,
                driverClassName, properties, poolConfig);
    }

    private void validate() {
        if (url == null || url.trim().isEmpty()) {
            throw new IllegalArgumentException("Database URL cannot be empty");
        }
        if (driverClassName == null || driverClassName.trim().isEmpty()) {
            throw new IllegalArgumentException("Driver class name cannot be empty");
        }
    }
}
