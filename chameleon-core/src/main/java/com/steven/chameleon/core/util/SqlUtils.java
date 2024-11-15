package com.steven.chameleon.core.util;

import org.apache.commons.lang3.StringUtils;

public class SqlUtils {

    /**
     * 转义SQL字符串中的特殊字符
     */
    public static String escapeSql(String str) {
        if (str == null) {
            return null;
        }
        return str.replace("'", "''")
                 .replace("\\", "\\\\")
                 .replace("%", "\\%")
                 .replace("_", "\\_");
    }
    
    /**
     * 构建模糊查询条件
     */
    public static String buildLikeValue(String value) {
        return "%" + escapeSql(value) + "%";
    }
    
    /**
     * 检查SQL注入风险
     */
    public static boolean hasSqlInjectionRisk(String sql) {
        if (StringUtils.isBlank(sql)) {
            return false;
        }
        
        String checkString = sql.toLowerCase();
        // 检查是否包含非法字符或关键字
        return checkString.contains("--") 
            || checkString.contains(";")
            || checkString.contains("/*")
            || checkString.contains("*/")
            || checkString.contains("exec ")
            || checkString.contains("union ")
            || checkString.contains("drop ")
            || checkString.contains("truncate ");
    }
}
