package com.steven.chameleon.core.transaction;

import com.steven.chameleon.core.exception.ChameleonException;

import java.sql.Connection;

/**
 * <template>desc</template>.
 *
 * @author: steven
 * @date: 2024/11/14 16:42
 */
public interface Transaction extends AutoCloseable {
    /**
     * 提交事务
     */
    void commit() throws ChameleonException;

    /**
     * 回滚事务
     */
    void rollback() throws ChameleonException;

    /**
     * 获取事务状态
     */
    TransactionStatus getStatus();

    /**
     * 设置事务隔离级别
     */
    void setIsolationLevel(IsolationLevel level) throws ChameleonException;

    /**
     * 获取事务隔离级别
     */
    IsolationLevel getIsolationLevel();

    /**
     * 标记事务只能回滚
     */
    void setRollbackOnly();

    /**
     * 判断事务是否活跃
     */
    boolean isActive();

    /**
     * 判断事务是否已完成
     */
    boolean isCompleted();

    /**
     * 获取事务连接
     */
    Connection getConnection();

    /**
     * 关闭事务资源
     */
    @Override
    void close() throws Exception;
}
