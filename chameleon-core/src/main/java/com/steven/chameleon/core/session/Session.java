package com.steven.chameleon.core.session;

import com.steven.chameleon.core.transaction.Transaction;

import java.sql.Connection;

/**
 * <template>desc</template>.
 *
 * @author: steven
 * @date: 2024/11/14 16:41
 */
public interface Session {
    /**
     * 获取数据库连接
     */
    Connection getConnection();

    /**
     * 开始事务
     */
    Transaction beginTransaction();

    /**
     * 关闭会话
     */
    void close();

    /**
     * 检查会话是否打开
     */
    boolean isOpen();

    /**
     * 清除会话
     */
    void clear();

    /**
     * 刷新会话
     */
    void flush();
}
