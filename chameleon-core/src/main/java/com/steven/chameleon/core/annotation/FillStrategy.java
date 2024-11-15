package com.steven.chameleon.core.annotation;

import lombok.Getter;

/**
 * @author: steven
 * @date: 2024/11/15 11:47
 */
@Getter
public enum FillStrategy {
    /**
     * 插入时填充
     */
    INSERT,

    /**
     * 更新时填充
     */
    UPDATE,

    /**
     * 插入和更新时都填充
     */
    INSERT_UPDATE
}
