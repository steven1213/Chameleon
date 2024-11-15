package com.steven.chameleon.core.sql;

import java.util.ArrayList;
import java.util.List;

/**
 * <template>desc<template>.
 *
 * @author: steven
 * @date: 2024/11/15 09:07
 */
public class SqlBuilder {
    private StringBuilder sql = new StringBuilder();
    private List<Object> params = new ArrayList<>();
    private boolean whereStarted = false;
    private boolean orderByStarted = false;

    public SqlBuilder select(String... columns) {
        sql.append("SELECT ");
        if (columns.length == 0) {
            sql.append("*");
        } else {
            sql.append(String.join(", ", columns));
        }
        return this;
    }

    public SqlBuilder from(String table) {
        sql.append(" FROM ").append(table);
        return this;
    }

    public SqlBuilder where(String condition, Object... parameters) {
        if (!whereStarted) {
            sql.append(" WHERE ");
            whereStarted = true;
        } else {
            sql.append(" AND ");
        }
        sql.append(condition);
        for (Object param : parameters) {
            params.add(param);
        }
        return this;
    }

    public SqlBuilder orderBy(String... columns) {
        if (!orderByStarted) {
            sql.append(" ORDER BY ");
            orderByStarted = true;
        } else {
            sql.append(", ");
        }
        sql.append(String.join(", ", columns));
        return this;
    }

    public SqlBuilder limit(int limit) {
        sql.append(" LIMIT ?");
        params.add(limit);
        return this;
    }

    public SqlBuilder offset(int offset) {
        sql.append(" OFFSET ?");
        params.add(offset);
        return this;
    }

    public String getSql() {
        return sql.toString();
    }

    public Object[] getParams() {
        return params.toArray();
    }

    public void clear() {
        sql = new StringBuilder();
        params.clear();
        whereStarted = false;
        orderByStarted = false;
    }
}