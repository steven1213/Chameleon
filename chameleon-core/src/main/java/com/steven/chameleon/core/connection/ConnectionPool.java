package com.steven.chameleon.core.connection;

import com.steven.chameleon.core.exception.ChameleonException;

import java.sql.Connection;

/**
 * <template>desc<template>.
 *
 * @author: steven
 * @date: 2024/11/14 16:57
 */
public interface ConnectionPool {

    /**
     * 获取连接
     */
    Connection acquire() throws ChameleonException;

    /**
     * 释放连接
     */
    void release(Connection connection) throws ChameleonException;

    /**
     * 关闭连接池
     */
    void shutdown() throws ChameleonException;

    /**
     * 获取连接池状态
     */
    PoolStatus getStatus();
}
