package com.steven.chameleon.core.util;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TableUtils {

    /**
     * 获取表的所有列名
     */
    public static List<String> getTableColumns(Connection conn, String tableName) throws SQLException {
        List<String> columns = new ArrayList<>();
        DatabaseMetaData metaData = conn.getMetaData();
        
        try (ResultSet rs = metaData.getColumns(null, null, tableName, null)) {
            while (rs.next()) {
                columns.add(rs.getString("COLUMN_NAME"));
            }
        }
        return columns;
    }
    
    /**
     * 检查表是否存在
     */
    public static boolean tableExists(Connection conn, String tableName) throws SQLException {
        DatabaseMetaData metaData = conn.getMetaData();
        try (ResultSet rs = metaData.getTables(null, null, tableName, new String[]{"TABLE"})) {
            return rs.next();
        }
    }
    
    /**
     * 获取建表语句中的列定义
     */
    public static String getColumnDefinition(ResultSet rs) throws SQLException {
        StringBuilder sb = new StringBuilder();
        sb.append(rs.getString("COLUMN_NAME")).append(" ");
        sb.append(rs.getString("TYPE_NAME"));
        
        int columnSize = rs.getInt("COLUMN_SIZE");
        if (columnSize > 0) {
            sb.append("(").append(columnSize);
            int decimalDigits = rs.getInt("DECIMAL_DIGITS");
            if (decimalDigits > 0) {
                sb.append(",").append(decimalDigits);
            }
            sb.append(")");
        }
        
        // 是否可为空
        if ("NO".equals(rs.getString("IS_NULLABLE"))) {
            sb.append(" NOT NULL");
        }
        
        return sb.toString();
    }
}
