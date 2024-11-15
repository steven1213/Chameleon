package com.steven.chameleon.core.parser;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

/**
 * <template>desc<template>.
 *
 * @author: steven
 * @date: 2024/11/15 11:57
 */
@Setter
@Getter
public class SqlStatement {
    private SqlType type;
    private String tableName;
    private List<String> columns;
    private String whereClause;
    private String orderByClause;
    private String groupByClause;
    private String limitClause;
    private Map<String, Object> parameters;
    private String sql;



    public static class Builder {
        private SqlStatement statement = new SqlStatement();

        public Builder type(SqlType type) {
            statement.type = type;
            return this;
        }

        public Builder tableName(String tableName) {
            statement.tableName = tableName;
            return this;
        }

        public Builder columns(List<String> columns) {
            statement.columns = columns;
            return this;
        }

        public Builder whereClause(String whereClause) {
            statement.whereClause = whereClause;
            return this;
        }

        public Builder orderByClause(String orderByClause) {
            statement.orderByClause = orderByClause;
            return this;
        }

        public Builder groupByClause(String groupByClause) {
            statement.groupByClause = groupByClause;
            return this;
        }

        public Builder limitClause(String limitClause) {
            statement.limitClause = limitClause;
            return this;
        }

        public Builder parameters(Map<String, Object> parameters) {
            statement.parameters = parameters;
            return this;
        }

        public SqlStatement build() {
            return statement;
        }
    }
}
