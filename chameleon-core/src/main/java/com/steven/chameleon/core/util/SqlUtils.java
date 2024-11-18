package com.steven.chameleon.core.util;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Pattern;

public final class SqlUtils {
    private SqlUtils() {
        throw new UnsupportedOperationException("Utility class");
    }

    private static final Pattern SELECT_PATTERN = Pattern.compile("^\\s*SELECT\\s+", Pattern.CASE_INSENSITIVE);
    private static final Pattern INSERT_PATTERN = Pattern.compile("^\\s*INSERT\\s+", Pattern.CASE_INSENSITIVE);
    private static final Pattern UPDATE_PATTERN = Pattern.compile("^\\s*UPDATE\\s+", Pattern.CASE_INSENSITIVE);
    private static final Pattern DELETE_PATTERN = Pattern.compile("^\\s*DELETE\\s+", Pattern.CASE_INSENSITIVE);
    
    private static final Pattern DANGEROUS_PATTERN = Pattern.compile(
        "\\b(DROP|TRUNCATE|ALTER|CREATE|RENAME|BACKUP|RESTORE)\\b", 
        Pattern.CASE_INSENSITIVE
    );

    /**
     * Check if the SQL is valid (not containing dangerous operations)
     *
     * @param sql SQL to check
     * @return true if valid, false otherwise
     */
    public static boolean isValidSql(String sql) {
        if (StringUtils.isBlank(sql)) {
            return false;
        }
        
        // Check for dangerous operations
        if (DANGEROUS_PATTERN.matcher(sql).find()) {
            return false;
        }
        
        // Check if it's a valid DML statement
        return SELECT_PATTERN.matcher(sql).find() ||
               INSERT_PATTERN.matcher(sql).find() ||
               UPDATE_PATTERN.matcher(sql).find() ||
               DELETE_PATTERN.matcher(sql).find();
    }

    public static boolean containsAggregateFunction(String sql) {
        if (sql == null || sql.trim().isEmpty()) {
            return false;
        }
        String upperSql = sql.toUpperCase();
        return upperSql.contains("COUNT(") || 
               upperSql.contains("SUM(") || 
               upperSql.contains("AVG(") || 
               upperSql.contains("MAX(") || 
               upperSql.contains("MIN(");
    }

    public static String addLimitAndOffset(String sql, int limit, int offset) {
        return sql + " LIMIT " + limit + " OFFSET " + offset;
    }

    public static String addOrderBy(String sql, String column, String direction) {
        return sql + " ORDER BY " + column + " " + direction;
    }

    public static String countSql(String sql) {
        String tableName = getTableName(sql);
        String whereClause = sql.contains("WHERE") ? sql.substring(sql.indexOf("WHERE")) : "";
        return String.format("SELECT COUNT(*) FROM %s %s", tableName, whereClause).trim();
    }

    public static String getTableName(String sql) {
        if (StringUtils.isBlank(sql)) {
            return null;    
        }
        
        String originalSql = sql;
        sql = sanitizeSql(sql).toUpperCase();
        
        String tableName = null;
        int tableNameStart = -1;
        int tableNameLength = 0;
        
        // 处理SELECT语句
        if (sql.startsWith("SELECT")) {
            int fromIndex = sql.indexOf(" FROM ");
            if (fromIndex != -1) {
                tableNameStart = fromIndex + 6;
                String afterFrom = sql.substring(tableNameStart).trim();
                tableNameLength = getTableNameLength(afterFrom);
            }
        }
        
        // 处理INSERT语句
        if (sql.startsWith("INSERT")) {
            int intoIndex = sql.indexOf(" INTO ");
            if (intoIndex != -1) {
                tableNameStart = intoIndex + 6;
                String afterInto = sql.substring(tableNameStart).trim();
                tableNameLength = getTableNameLength(afterInto);
            }
        }
        
        // 处理UPDATE语句
        if (sql.startsWith("UPDATE")) {
            String afterUpdate = sql.substring(7).trim();
            tableNameStart = 0;
            tableNameLength = getTableNameLength(afterUpdate);
        }
        
        // 处理DELETE语句
        if (sql.startsWith("DELETE")) {
            int fromIndex = sql.indexOf(" FROM ");
            if (fromIndex != -1) {
                tableNameStart = fromIndex + 6;
                String afterFrom = sql.substring(tableNameStart).trim();
                tableNameLength = getTableNameLength(afterFrom);
            }
        }
        
        return tableNameStart != -1 ? 
            originalSql.substring(tableNameStart, tableNameStart + tableNameLength).trim() : 
            null;
    }

    private static int getTableNameLength(String sql) {
        for (int i = 0; i < sql.length(); i++) {
            char c = sql.charAt(i);
            if (Character.isWhitespace(c) || c == '(' || c == ',' || c == ';') {
                return i;
            }
        }
        return sql.length();
    }

    public static String sanitizeSql(String sql) {
        if (sql == null) return null;
        // 移除多条SQL语句，只保留第一条
        sql = sql.split(";")[0];
        // 移除SQL注释并规范化空格
        return sql.replaceAll("--.*$", "")
                 .replaceAll("/\\*.*?\\*/", "")
                 .replaceAll("\\s+", " ")
                 .trim();
    }
}
