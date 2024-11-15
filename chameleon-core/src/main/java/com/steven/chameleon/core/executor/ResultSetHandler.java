package com.steven.chameleon.core.executor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * <template>desc<template>.
 *
 * @author: steven
 * @date: 2024/11/14 17:33
 */
public interface ResultSetHandler<T> {
    /**
     * 处理结果集
     */
    List<T> handle(ResultSet rs, Class<T> type) throws SQLException;

    /**
     * 处理单行结果
     */
    T handleSingle(ResultSet rs, Class<T> type) throws SQLException;
}
