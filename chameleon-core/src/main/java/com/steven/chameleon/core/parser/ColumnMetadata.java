package com.steven.chameleon.core.parser;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

/**
 * <template>列元数据<template>.
 *
 * @author: steven
 * @date: 2024/11/15 13:16
 */
@Setter
@Getter
public class ColumnMetadata {
    private String name;
    private String description;
    private String type;
    private boolean isPrimary;
    private boolean isAutoIncrement;
    private int length;
    private boolean nullable;
    private String defaultValue;
    private String comment;
    private Map<String, Object> extra;
}
