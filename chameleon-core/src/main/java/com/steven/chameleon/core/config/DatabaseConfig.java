package com.steven.chameleon.core.config;

import java.util.Properties;

/**
 * <template>desc<template>.
 *
 * @author: steven
 * @date: 2024/11/14 16:53
 */
public interface DatabaseConfig {
    /**
     * 获取数据库URL
     */
    String getUrl();

    /**
     * 获取用户名
     */
    String getUsername();

    /**
     * 获取密码
     */
    String getPassword();

    /**
     * 获取驱动类名
     */
    String getDriverClassName();

    /**
     * 获取连接池配置
     */
    PoolConfig getPoolConfig();

    /**
     * 获取所有配置属性
     */
    Properties getProperties();
}
