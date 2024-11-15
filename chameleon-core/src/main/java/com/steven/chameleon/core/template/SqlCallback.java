package com.steven.chameleon.core.template;

/**
 * <template>desc</template>.
 *
 * @author: steven
 * @date: 2024/11/15 09:07
 */

import java.sql.SQLException;

/**
 * SQL回调接口
 */
@FunctionalInterface
public interface SqlCallback<T> {
    T execute() throws SQLException;
}
