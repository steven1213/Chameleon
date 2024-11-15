package com.steven.chameleon.core.page;

import java.util.List;

/**
 * <template>desc<template>.
 *
 * @author: steven
 * @date: 2024/11/15 10:49
 */
public class PageResult<T> {
    private final List<T> content;
    private final long total;
    private final int pageNum;
    private final int pageSize;
    private final int totalPages;

    public PageResult(List<T> content, long total, PageRequest pageRequest) {
        this.content = content;
        this.total = total;
        this.pageNum = pageRequest.getPageNum();
        this.pageSize = pageRequest.getPageSize();
        this.totalPages = calculateTotalPages(total, pageRequest.getPageSize());
    }

    private int calculateTotalPages(long total, int pageSize) {
        return pageSize == 0 ? 1 : (int) Math.ceil((double) total / (double) pageSize);
    }

    public List<T> getContent() {
        return content;
    }

    public long getTotal() {
        return total;
    }

    public int getPageNum() {
        return pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public boolean hasNext() {
        return pageNum < totalPages;
    }

    public boolean hasPrevious() {
        return pageNum > 1;
    }

    public boolean isFirst() {
        return pageNum == 1;
    }

    public boolean isLast() {
        return pageNum == totalPages;
    }
}
