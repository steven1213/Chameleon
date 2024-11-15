package com.steven.chameleon.core.executor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * <template>desc</template>.
 *
 * @author: steven
 * @date: 2024/11/14 17:31
 */
public interface StatementHandler {
    /**
     * 创建PreparedStatement
     */
    PreparedStatement prepare(Connection connection, String sql, Object... params)
            throws SQLException;

    /**
     * 设置参数
     */
    void setParameters(PreparedStatement ps, Object... params) throws SQLException;
}
