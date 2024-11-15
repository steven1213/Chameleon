package com.steven.chameleon.core.serializer;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.steven.chameleon.core.exception.ChameleonException;
import com.steven.chameleon.core.exception.ErrorCode;

/**
 * <template>desc<template>.
 *
 * @author: steven
 * @date: 2024/11/15 10:58
 */
public class JsonSerializer implements Serializer {
    private final ObjectMapper objectMapper;

    public JsonSerializer() {
        this.objectMapper = new ObjectMapper();
        // 配置ObjectMapper
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Override
    public byte[] serialize(Object obj) {
        try {
            return objectMapper.writeValueAsBytes(obj);
        } catch (Exception e) {
            throw new ChameleonException("Failed to serialize object",
                    ErrorCode.SERIALIZATION_ERROR, e);
        }
    }

    @Override
    public Object deserialize(byte[] bytes) {
        try {
            return objectMapper.readValue(bytes, Object.class);
        } catch (Exception e) {
            throw new ChameleonException("Failed to deserialize object",
                    ErrorCode.SERIALIZATION_ERROR, e);
        }
    }
}