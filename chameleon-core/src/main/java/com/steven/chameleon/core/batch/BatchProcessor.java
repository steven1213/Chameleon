package com.steven.chameleon.core.batch;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * <template>desc<template>.
 *
 * @author: steven
 * @date: 2024/11/15 10:50
 */
public class BatchProcessor<T> {
    private final List<T> items = new ArrayList<>();
    private final int batchSize;
    private final Consumer<List<T>> batchOperation;

    public BatchProcessor(int batchSize, Consumer<List<T>> batchOperation) {
        this.batchSize = batchSize;
        this.batchOperation = batchOperation;
    }

    public void add(T item) {
        items.add(item);
        if (items.size() >= batchSize) {
            flush();
        }
    }

    public void addAll(List<T> newItems) {
        for (T item : newItems) {
            add(item);
        }
    }

    public void flush() {
        if (!items.isEmpty()) {
            batchOperation.accept(new ArrayList<>(items));
            items.clear();
        }
    }

    public int getPendingCount() {
        return items.size();
    }
}
