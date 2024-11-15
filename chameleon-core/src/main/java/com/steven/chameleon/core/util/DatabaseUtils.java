package com.steven.chameleon.core.util;

import java.sql.*;

public class DatabaseUtils {

    /**
     * 关闭数据库连接
     */
    public static void closeQuietly(Connection conn) {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException e) {
            // 静默关闭
        }
    }

    /**
     * 关闭Statement
     */
    public static void closeQuietly(Statement stmt) {
        try {
            if (stmt != null) {
                stmt.close();
            }
        } catch (SQLException e) {
            // 静默关闭
        }
    }

    /**
     * 关闭ResultSet
     */
    public static void closeQuietly(ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            // 静默关闭
        }
    }

    /**
     * 获取数据库类型
     */
    public static String getDatabaseType(Connection conn) throws SQLException {
        return conn.getMetaData().getDatabaseProductName().toLowerCase();
    }

    /**
     * 测试数据库连接
     */
    public static boolean testConnection(String url, String username, String password) {
        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
}
