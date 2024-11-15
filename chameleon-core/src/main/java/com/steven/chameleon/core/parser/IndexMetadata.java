package com.steven.chameleon.core.parser;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * <template>索引元数据<template>.
 *
 * @author: steven
 * @date: 2024/11/15 13:16
 */
@Setter
@Getter
public class IndexMetadata {
    private String name;
    private List<String> columns;
    private boolean unique;
    private String type;
    private String comment;
}
