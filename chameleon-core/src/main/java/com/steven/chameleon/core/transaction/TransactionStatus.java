package com.steven.chameleon.core.transaction;

import lombok.Getter;

/**
 * @author: steven
 * @date: 2024/11/14 16:50
 */
@Getter
public enum TransactionStatus {
    /**
     * 活跃状态
     */
    ACTIVE("Transaction is active"),

    /**
     * 已提交
     */
    COMMITTED("Transaction has been committed"),

    /**
     * 已回滚
     */
    ROLLED_BACK("Transaction has been rolled back"),

    /**
     * 标记为只能回滚
     */
    MARKED_ROLLBACK("Transaction is marked for rollback"),

    /**
     * 未知状态
     */
    UNKNOWN("Transaction status is unknown");

    private final String description;

    TransactionStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public boolean isCompleted() {
        return this == COMMITTED || this == ROLLED_BACK;
    }

    public boolean canCommit() {
        return this == ACTIVE;
    }
}
