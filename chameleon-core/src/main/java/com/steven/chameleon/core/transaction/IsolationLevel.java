package com.steven.chameleon.core.transaction;

import lombok.Getter;

import java.sql.Connection;

/**
 * @author: steven
 * @date: 2024/11/14 16:50
 */
@Getter
public enum IsolationLevel {
    /**
     * 使用数据库默认的隔离级别
     */
    DEFAULT(-1),

    /**
     * 读未提交
     */
    READ_UNCOMMITTED(Connection.TRANSACTION_READ_UNCOMMITTED),

    /**
     * 读已提交
     */
    READ_COMMITTED(Connection.TRANSACTION_READ_COMMITTED),

    /**
     * 可重复读
     */
    REPEATABLE_READ(Connection.TRANSACTION_REPEATABLE_READ),

    /**
     * 串行化
     */
    SERIALIZABLE(Connection.TRANSACTION_SERIALIZABLE);

    private final int level;

    IsolationLevel(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }

    /**
     * 根据JDBC隔离级别获取对应的枚举值
     */
    public static IsolationLevel valueOf(int level) {
        for (IsolationLevel isolationLevel : values()) {
            if (isolationLevel.level == level) {
                return isolationLevel;
            }
        }
        return DEFAULT;
    }

    /**
     * 判断是否为有效的隔离级别
     */
    public boolean isValid() {
        return this != DEFAULT;
    }

    /**
     * 获取描述信息
     */
    public String getDescription() {
        switch (this) {
            case READ_UNCOMMITTED:
                return "Read Uncommitted - Lowest isolation level, dirty reads are possible";
            case READ_COMMITTED:
                return "Read Committed - Prevents dirty reads";
            case REPEATABLE_READ:
                return "Repeatable Read - Prevents dirty and non-repeatable reads";
            case SERIALIZABLE:
                return "Serializable - Highest isolation level, prevents all concurrency issues";
            default:
                return "Default - Uses the database's default isolation level";
        }
    }
}