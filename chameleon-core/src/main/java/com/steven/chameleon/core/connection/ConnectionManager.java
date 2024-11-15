package com.steven.chameleon.core.connection;

import com.steven.chameleon.core.exception.ChameleonException;

import java.sql.Connection;

/**
 * <template>desc</template>.
 *
 * @author: steven
 * @date: 2024/11/14 16:55
 */
public interface ConnectionManager {
    /**
     * 获取数据库连接
     */
    Connection getConnection() throws ChameleonException;

    /**
     * 释放连接
     */
    void releaseConnection(Connection connection) throws ChameleonException;

    /**
     * 关闭所有连接
     */
    void closeAll() throws ChameleonException;

    /**
     * 获取连接池状态
     */
    PoolStatus getPoolStatus();
}
