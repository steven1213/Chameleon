package com.steven.chameleon.core.annotation;

import lombok.Getter;

/**
 * @author: steven
 * @date: 2024/11/15 11:54
 */
@Getter
public enum SerializeType {
    /**
     * JSON序列化
     */
    JSON,

    /**
     * XML序列化
     */
    XML,

    /**
     * 自定义序列化
     */
    CUSTOM
}
