package com.steven.chameleon.core.monitor;

/**
 * <template>基础统计信息<template>.
 *
 * @author: steven
 * @date: 2024/11/15 11:36
 */
public class BasicStats {
    private final long totalQueries;
    private final long totalUpdates;
    private final long totalErrors;
    private final long totalQueryTime;
    private final long maxQueryTime;
    private final long minQueryTime;

    public BasicStats(long totalQueries, long totalUpdates, long totalErrors,
                      long totalQueryTime, long maxQueryTime, long minQueryTime) {
        this.totalQueries = totalQueries;
        this.totalUpdates = totalUpdates;
        this.totalErrors = totalErrors;
        this.totalQueryTime = totalQueryTime;
        this.maxQueryTime = maxQueryTime;
        this.minQueryTime = minQueryTime;
    }

    /**
     * 获取总查询次数
     */
    public long getTotalQueries() {
        return totalQueries;
    }

    /**
     * 获取总更新次数
     */
    public long getTotalUpdates() {
        return totalUpdates;
    }

    /**
     * 获取总错误次数
     */
    public long getTotalErrors() {
        return totalErrors;
    }

    /**
     * 获取总执行时间
     */
    public long getTotalExecutionTime() {
        return totalQueryTime;
    }

    /**
     * 获取最大执行时间
     */
    public long getMaxQueryTime() {
        return maxQueryTime;
    }

    /**
     * 获取最小执行时间
     */
    public long getMinQueryTime() {
        return minQueryTime;
    }

    /**
     * 获取平均执行时间
     */
    public double getAverageQueryTime() {
        long total = totalQueries + totalUpdates;
        return total == 0 ? 0 : (double) totalQueryTime / total;
    }

    /**
     * 获取总操作次数
     */
    public long getTotalOperations() {
        return totalQueries + totalUpdates;
    }

    /**
     * 获取错误率
     */
    public double getErrorRate() {
        long total = getTotalOperations();
        return total == 0 ? 0 : (double) totalErrors / total * 100;
    }

    @Override
    public String toString() {
        return String.format(
                "BasicStats{queries=%d, updates=%d, errors=%d, avgTime=%.2fms, maxTime=%dms, minTime=%dms}",
                totalQueries, totalUpdates, totalErrors, getAverageQueryTime(), maxQueryTime, minQueryTime
        );
    }
}
