package com.steven.chameleon.core.parser;

/**
 * <template>解析器接口</template>.
 *
 * @author: steven
 * @date: 2024/11/15 11:55
 */
public interface Parser<T> {
    /**
     * 解析
     */
    T parse(String content);
}