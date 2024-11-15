package com.steven.chameleon.core.interceptor;

import java.sql.Connection;

/**
 * <template>desc</template>.
 *
 * @author: steven
 * @date: 2024/11/15 10:55
 */
public interface SqlInterceptor {
    /**
     * 执行前处理
     */
    void beforeExecute(String sql, Object[] params, Connection conn);

    /**
     * 执行后处理
     */
    void afterExecute(String sql, Object[] params, Connection conn, long timeMillis);

    /**
     * 执行异常处理
     */
    void onError(String sql, Object[] params, Connection conn, Exception e);
}
