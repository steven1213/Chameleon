package com.steven.chameleon.core.interceptor;

import com.steven.chameleon.core.monitor.SqlMonitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.util.Arrays;

/**
 * <template>desc<template>.
 *
 * @author: steven
 * @date: 2024/11/15 10:55
 */
public class DefaultSqlInterceptor implements SqlInterceptor {
    private final SqlMonitor monitor;
    private final Logger logger = LoggerFactory.getLogger(DefaultSqlInterceptor.class);

    public DefaultSqlInterceptor(SqlMonitor monitor) {
        this.monitor = monitor;
    }

    @Override
    public void beforeExecute(String sql, Object[] params, Connection conn) {
        if (logger.isDebugEnabled()) {
            logger.debug("Executing SQL: {} with params: {}", sql, Arrays.toString(params));
        }
    }

    @Override
    public void afterExecute(String sql, Object[] params, Connection conn, long timeMillis) {
        if (sql.trim().toLowerCase().startsWith("select")) {
            monitor.recordQuery(timeMillis);
        } else {
            monitor.recordUpdate(timeMillis);
        }

        if (logger.isDebugEnabled()) {
            logger.debug("SQL execution completed in {}ms", timeMillis);
        }
    }

    @Override
    public void onError(String sql, Object[] params, Connection conn, Exception e) {
        monitor.recordError();
        logger.error("SQL execution error: {} with params: {}", sql, Arrays.toString(params), e);
    }
}
