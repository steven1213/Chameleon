package com.steven.chameleon.auth.enums;

import lombok.Getter;

/**
 * @author: steven
 * @date: 2024/11/18 11:11
 */
@Getter
public enum LoginType {
    LOCAL("本地账号登录"),    // 本地账号登录
    LDAP("LDAP登录"),     // LDAP登录
    SSO("预留其他登录方式");       // 预留其他登录方式

    private final String description;

    LoginType(String description) {
        this.description = description;
    }
}
