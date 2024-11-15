package com.steven.chameleon.core.transaction;

/**
 * <template>desc</template>.
 *
 * @author: steven
 * @date: 2024/11/14 17:41
 */
public interface TransactionSynchronization {
    /**
     * 事务提交前
     */
    default void beforeCommit() {}

    /**
     * 事务提交后
     */
    default void afterCommit() {}

    /**
     * 事务完成后
     */
    default void afterCompletion(TransactionStatus status) {}
}
