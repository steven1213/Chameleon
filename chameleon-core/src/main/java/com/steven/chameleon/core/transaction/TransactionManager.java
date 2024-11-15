package com.steven.chameleon.core.transaction;

import java.sql.SQLException;

/**
 * <template>desc</template>.
 *
 * @author: steven
 * @date: 2024/11/14 17:38
 */
public interface TransactionManager {
    /**
     * 开始事务
     */
    void beginTransaction() throws SQLException;

    /**
     * 提交事务
     */
    void commit() throws SQLException;

    /**
     * 回滚事务
     */
    void rollback();

    /**
     * 判断事务是否激活
     */
    boolean isTransactionActive();

    /**
     * 获取当前事务
     */
    Transaction getCurrentTransaction();
}
