package com.steven.chameleon.core.serializer;

/**
 * <template>desc<template>.
 *
 * @author: steven
 * @date: 2024/11/15 10:57
 */
public interface Serializer {
    byte[] serialize(Object obj);
    Object deserialize(byte[] bytes);
}
