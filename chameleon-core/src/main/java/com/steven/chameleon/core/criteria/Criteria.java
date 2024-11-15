package com.steven.chameleon.core.criteria;

import java.util.ArrayList;
import java.util.List;

/**
 * <template>desc<template>.
 *
 * @author: steven
 * @date: 2024/11/15 10:50
 */
public class Criteria {
    private final List<Criterion> criteria = new ArrayList<>();
    private final List<Object> parameters = new ArrayList<>();

    public Criteria eq(String column, Object value) {
        if (value != null) {
            criteria.add(new Criterion(column, "=", value));
            parameters.add(value);
        }
        return this;
    }

    public Criteria ne(String column, Object value) {
        if (value != null) {
            criteria.add(new Criterion(column, "<>", value));
            parameters.add(value);
        }
        return this;
    }

    public Criteria gt(String column, Object value) {
        if (value != null) {
            criteria.add(new Criterion(column, ">", value));
            parameters.add(value);
        }
        return this;
    }

    public Criteria lt(String column, Object value) {
        if (value != null) {
            criteria.add(new Criterion(column, "<", value));
            parameters.add(value);
        }
        return this;
    }

    public Criteria like(String column, String value) {
        if (value != null) {
            criteria.add(new Criterion(column, "LIKE", "%" + value + "%"));
            parameters.add("%" + value + "%");
        }
        return this;
    }

    public Criteria in(String column, List<?> values) {
        if (values != null && !values.isEmpty()) {
            StringBuilder placeholders = new StringBuilder();
            for (int i = 0; i < values.size(); i++) {
                if (i > 0) {
                    placeholders.append(",");
                }
                placeholders.append("?");
                parameters.add(values.get(i));
            }
            criteria.add(new Criterion(column, "IN", "(" + placeholders + ")"));
        }
        return this;
    }

    public String toSql() {
        if (criteria.isEmpty()) {
            return "";
        }

        StringBuilder sql = new StringBuilder(" WHERE ");
        for (int i = 0; i < criteria.size(); i++) {
            if (i > 0) {
                sql.append(" AND ");
            }
            sql.append(criteria.get(i).toSql());
        }
        return sql.toString();
    }

    public Object[] getParameters() {
        return parameters.toArray();
    }

    private static class Criterion {
        private final String column;
        private final String operator;
        private final Object value;

        public Criterion(String column, String operator, Object value) {
            this.column = column;
            this.operator = operator;
            this.value = value;
        }

        public String toSql() {
            if ("IN".equals(operator)) {
                return column + " " + operator + " " + value;
            }
            return column + " " + operator + " ?";
        }
    }
}
