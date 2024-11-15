package com.steven.chameleon.core.parser;

import lombok.Getter;

/**
 * <template>SQL语句类型<template>.
 *
 * @author: steven
 * @date: 2024/11/15 11:57
 */
@Getter
public enum SqlType {
    SELECT,
    INSERT,
    UPDATE,
    DELETE,
    CREATE,
    ALTER,
    DROP,
    TRUNCATE,
    UNKNOWN
}

