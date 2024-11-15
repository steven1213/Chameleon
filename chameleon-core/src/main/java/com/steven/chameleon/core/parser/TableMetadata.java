package com.steven.chameleon.core.parser;

import lombok.Getter;
import lombok.Setter;

/**
 * <template>表元数据<template>.
 *
 * @author: steven
 * @date: 2024/11/15 13:16
 */
@Setter
@Getter
public class TableMetadata {
    private String name;
    private String description;
    private String engine;
    private String charset;
    private String collate;
}
