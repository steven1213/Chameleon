package com.steven.chameleon.core.util;

import java.util.HashMap;
import java.util.Map;

public class DataTypeUtils {

    private static final Map<String, String> MYSQL_TO_ORACLE_TYPE_MAP = new HashMap<>();
    private static final Map<String, String> ORACLE_TO_MYSQL_TYPE_MAP = new HashMap<>();

    static {
        // MySQL到Oracle的类型映射
        MYSQL_TO_ORACLE_TYPE_MAP.put("varchar", "varchar2");
        MYSQL_TO_ORACLE_TYPE_MAP.put("datetime", "timestamp");
        MYSQL_TO_ORACLE_TYPE_MAP.put("bigint", "number(19)");
        MYSQL_TO_ORACLE_TYPE_MAP.put("int", "number(10)");
        MYSQL_TO_ORACLE_TYPE_MAP.put("tinyint", "number(3)");
        // ... 添加更多映射

        // Oracle到MySQL的类型映射
        ORACLE_TO_MYSQL_TYPE_MAP.put("varchar2", "varchar");
        ORACLE_TO_MYSQL_TYPE_MAP.put("number", "decimal");
        ORACLE_TO_MYSQL_TYPE_MAP.put("clob", "text");
        // ... 添加更多映射
    }

    /**
     * MySQL类型转Oracle类型
     */
    public static String mysqlToOracleType(String mysqlType) {
        String baseType = mysqlType.toLowerCase().split("\\s*\\(")[0];
        return MYSQL_TO_ORACLE_TYPE_MAP.getOrDefault(baseType, mysqlType);
    }

    /**
     * Oracle类型转MySQL类型
     */
    public static String oracleToMysqlType(String oracleType) {
        String baseType = oracleType.toLowerCase().split("\\s*\\(")[0];
        return ORACLE_TO_MYSQL_TYPE_MAP.getOrDefault(baseType, oracleType);
    }

    /**
     * 判断是否为数值类型
     */
    public static boolean isNumericType(String dataType) {
        String type = dataType.toLowerCase();
        return type.contains("int") 
            || type.contains("float") 
            || type.contains("double") 
            || type.contains("decimal") 
            || type.contains("number");
    }
}
