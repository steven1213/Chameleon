package com.steven.chameleon.core.parser;

import java.util.List;

/**
 * <template>SQL解析器接口<template>.
 *
 * @author: steven
 * @date: 2024/11/15 11:55
 */
public interface SqlParser extends Parser<SqlStatement> {
    /**
     * 获取SQL类型
     */
    SqlType getSqlType(String sql);

    /**
     * 获取表名
     */
    String getTableName(String sql);

    /**
     * 获取查询字段
     */
    List<String> getSelectColumns(String sql);

    /**
     * 获取条件
     */
    String getWhereClause(String sql);

    /**
     * 获取排序
     */
    String getOrderByClause(String sql);

    /**
     * 获取分组
     */
    String getGroupByClause(String sql);

    /**
     * 获取限制
     */
    String getLimitClause(String sql);
}
