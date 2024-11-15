package com.steven.chameleon.core.parser.impl;

import com.steven.chameleon.core.parser.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * <template>desc<template>.
 *
 * @author: steven
 * @date: 2024/11/15 13:20
 */
public class DefaultAnnotationParser implements AnnotationParser {
    private static final Logger logger = LoggerFactory.getLogger(DefaultAnnotationParser.class);

    @Override
    public EntityMetadata parse(String content) {
        try {
            Class<?> clazz = Class.forName(content);
            return parse(clazz);
        } catch (ClassNotFoundException e) {
            logger.error("Failed to parse class: " + content, e);
            throw new RuntimeException("Failed to parse class: " + content, e);
        }
    }

    public EntityMetadata parse(Class<?> clazz) {
        EntityMetadata metadata = new EntityMetadata();
        metadata.setTable(parseTableAnnotation(clazz));
        metadata.setColumns(parseColumnAnnotations(clazz));
        metadata.setIndexes(parseIndexAnnotations(clazz));
        return metadata;
    }

    @Override
    public List<IndexMetadata> parseIndexAnnotations(Class<?> clazz) {
        // TODO: 实现索引注解解析逻辑
        return new ArrayList<>();
    }

    @Override
    public TableMetadata parseTableAnnotation(Class<?> clazz) {
        // TODO: 实现表注解解析逻辑
        return new TableMetadata();
    }

    @Override
    public List<ColumnMetadata> parseColumnAnnotations(Class<?> clazz) {
        // TODO: 实现列注解解析逻辑
        return new ArrayList<>();
    }

}