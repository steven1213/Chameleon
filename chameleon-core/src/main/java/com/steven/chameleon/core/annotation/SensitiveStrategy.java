package com.steven.chameleon.core.annotation;

import lombok.Getter;

/**
 * @author: steven
 * @date: 2024/11/15 11:46
 */
@Getter
public enum SensitiveStrategy {
    /**
     * 手机号码
     */
    PHONE,

    /**
     * 邮箱
     */
    EMAIL,

    /**
     * 身份证号
     */
    ID_CARD,

    /**
     * 银行卡号
     */
    BANK_CARD,

    /**
     * 自定义
     */
    CUSTOM
}
