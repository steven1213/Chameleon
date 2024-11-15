package com.steven.chameleon.core.page;

/**
 * <template>desc<template>.
 *
 * @author: steven
 * @date: 2024/11/15 10:49
 */
public class PageRequest {
    private final int pageNum;
    private final int pageSize;
    private final String orderBy;
    private final boolean asc;

    private PageRequest(Builder builder) {
        this.pageNum = builder.pageNum;
        this.pageSize = builder.pageSize;
        this.orderBy = builder.orderBy;
        this.asc = builder.asc;
    }

    public int getPageNum() {
        return pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public boolean isAsc() {
        return asc;
    }

    public int getOffset() {
        return (pageNum - 1) * pageSize;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private int pageNum = 1;
        private int pageSize = 10;
        private String orderBy;
        private boolean asc = true;

        public Builder pageNum(int pageNum) {
            this.pageNum = pageNum;
            return this;
        }

        public Builder pageSize(int pageSize) {
            this.pageSize = pageSize;
            return this;
        }

        public Builder orderBy(String orderBy) {
            this.orderBy = orderBy;
            return this;
        }

        public Builder asc(boolean asc) {
            this.asc = asc;
            return this;
        }

        public PageRequest build() {
            return new PageRequest(this);
        }
    }
}
