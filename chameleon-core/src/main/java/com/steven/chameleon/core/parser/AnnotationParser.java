package com.steven.chameleon.core.parser;

import java.util.List;

/**
 * <template>注解解析器接口<template>.
 *
 * @author: steven
 * @date: 2024/11/15 11:56
 */
public interface AnnotationParser extends Parser<EntityMetadata> {
    /**
     * 解析类注解
     */
    TableMetadata parseTableAnnotation(Class<?> clazz);

    /**
     * 解析字段注解
     */
    List<ColumnMetadata> parseColumnAnnotations(Class<?> clazz);

    /**
     * 解析索引注解
     */
    List<IndexMetadata> parseIndexAnnotations(Class<?> clazz);
}
