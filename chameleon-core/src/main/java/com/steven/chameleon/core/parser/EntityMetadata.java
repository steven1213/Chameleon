package com.steven.chameleon.core.parser;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * <template>实体元数据<template>.
 *
 * @author: steven
 * @date: 2024/11/15 11:58
 */
@Setter
@Getter
public class EntityMetadata {
    private TableMetadata table;
    private List<ColumnMetadata> columns;
    private List<IndexMetadata> indexes;


}
