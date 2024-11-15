package com.steven.chameleon.core.annotation;

import lombok.Getter;

/**
 * @author: steven
 * @date: 2024/11/15 11:52
 */
@Getter
public enum CascadeType {
    /**
     * 级联更新
     */
    UPDATE,

    /**
     * 级联删除
     */
    DELETE,

    /**
     * 级联查询
     */
    SELECT
}
