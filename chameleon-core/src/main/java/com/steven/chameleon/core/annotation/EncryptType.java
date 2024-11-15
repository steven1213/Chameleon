package com.steven.chameleon.core.annotation;

import lombok.Getter;

/**
 * @author: steven
 * @date: 2024/11/15 11:53
 */
@Getter
public enum EncryptType {
    /**
     * AES加密
     */
    AES,

    /**
     * DES加密
     */
    DES,

    /**
     * RSA加密
     */
    RSA,

    /**
     * 自定义加密
     */
    CUSTOM
}
