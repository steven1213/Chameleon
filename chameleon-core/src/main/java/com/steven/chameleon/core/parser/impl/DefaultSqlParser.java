package com.steven.chameleon.core.parser.impl;

import com.steven.chameleon.core.parser.SqlParser;
import com.steven.chameleon.core.parser.SqlStatement;
import com.steven.chameleon.core.parser.SqlType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * <template>默认SQL解析器实现<template>.
 *
 * @author: steven
 * @date: 2024/11/15 13:18
 */
public class DefaultSqlParser implements SqlParser {
    private static final Logger logger = LoggerFactory.getLogger(DefaultSqlParser.class);

    private static final Pattern SELECT_PATTERN = Pattern.compile(
            "SELECT\\s+(.+?)\\s+FROM\\s+(\\w+)(?:\\s+WHERE\\s+(.+?))?(?:\\s+GROUP\\s+BY\\s+(.+?))?(?:\\s+ORDER\\s+BY\\s+(.+?))?(?:\\s+LIMIT\\s+(.+))?$",
            Pattern.CASE_INSENSITIVE);

    @Override
    public SqlStatement parse(String sql) {
        SqlType type = getSqlType(sql);
        switch (type) {
            case SELECT:
                return parseSelect(sql);
            case INSERT:
                return parseInsert(sql);
            case UPDATE:
                return parseUpdate(sql);
            case DELETE:
                return parseDelete(sql);
            default:
                throw new IllegalArgumentException("Unsupported SQL type: " + type);
        }
    }

    @Override
    public SqlType getSqlType(String sql) {
        String upperSql = sql.trim().toUpperCase();
        if (upperSql.startsWith("SELECT")) return SqlType.SELECT;
        if (upperSql.startsWith("INSERT")) return SqlType.INSERT;
        if (upperSql.startsWith("UPDATE")) return SqlType.UPDATE;
        if (upperSql.startsWith("DELETE")) return SqlType.DELETE;
        return SqlType.UNKNOWN;
    }

    @Override
    public String getGroupByClause(String sql) {
        Matcher matcher = SELECT_PATTERN.matcher(sql.trim());
        if (matcher.find()) {
            return matcher.group(4); // 返回GROUP BY子句，可能为null
        }
        return null;
    }

    @Override
    public String getWhereClause(String sql) {
        Matcher matcher = SELECT_PATTERN.matcher(sql.trim());
        if (matcher.find()) {
            return matcher.group(3); // 返回WHERE子句，可能为null
        }
        return null;
    }

    @Override
    public String getLimitClause(String sql) {
        Matcher matcher = SELECT_PATTERN.matcher(sql.trim());
        if (matcher.find()) {
            return matcher.group(6); // 返回LIMIT子句，可能为null
        }
        return null;
    }

    @Override
    public String getTableName(String sql) {
        Matcher matcher = SELECT_PATTERN.matcher(sql.trim());
        if (matcher.find()) {
            return matcher.group(2); // 返回FROM子句后的表名
        }
        return null;
    }

    @Override
    public String getOrderByClause(String sql) {
        Matcher matcher = SELECT_PATTERN.matcher(sql.trim());
        if (matcher.find()) {
            return matcher.group(5); // 返回ORDER BY子句，可能为null
        }
        return null;
    }

    @Override
    public List<String> getSelectColumns(String sql) {
        Matcher matcher = SELECT_PATTERN.matcher(sql.trim());
        if (matcher.find()) {
            String columnsStr = matcher.group(1);
            return Arrays.asList(columnsStr.split("\\s*,\\s*"));
        }
        return Collections.emptyList();
    }

    private SqlStatement parseSelect(String sql) {
        SqlStatement statement = new SqlStatement();
        statement.setType(SqlType.SELECT);
        statement.setSql(sql);
        statement.setTableName(getTableName(sql));
        statement.setColumns(getSelectColumns(sql));
        statement.setWhereClause(getWhereClause(sql));
        statement.setGroupByClause(getGroupByClause(sql));
        statement.setOrderByClause(getOrderByClause(sql));
        statement.setLimitClause(getLimitClause(sql));
        return statement;
    }

    private SqlStatement parseInsert(String sql) {
        SqlStatement statement = new SqlStatement();
        statement.setType(SqlType.INSERT);
        statement.setSql(sql);
        // TODO: 实现具体的INSERT语句解析逻辑
        return statement;
    }

    private SqlStatement parseUpdate(String sql) {
        SqlStatement statement = new SqlStatement();
        statement.setType(SqlType.UPDATE);
        statement.setSql(sql);
        // TODO: 实现具体的UPDATE语句解析逻辑
        return statement;
    }

    private SqlStatement parseDelete(String sql) {
        SqlStatement statement = new SqlStatement();
        statement.setType(SqlType.DELETE);
        statement.setSql(sql);
        // TODO: 实现具体的DELETE语句解析逻辑
        return statement;
    }
}
