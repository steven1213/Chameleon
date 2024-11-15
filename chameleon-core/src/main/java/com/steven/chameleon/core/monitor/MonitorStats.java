package com.steven.chameleon.core.monitor;

/**
 * <template>desc<template>.
 *
 * @author: steven
 * @date: 2024/11/15 10:54
 */
public class MonitorStats {
    private final long totalQueries;
    private final long totalUpdates;
    private final long totalErrors;
    private final long averageTime;
    private final long maxTime;
    private final long minTime;

    public MonitorStats(long totalQueries, long totalUpdates, long totalErrors,
                        long averageTime, long maxTime, long minTime) {
        this.totalQueries = totalQueries;
        this.totalUpdates = totalUpdates;
        this.totalErrors = totalErrors;
        this.averageTime = averageTime;
        this.maxTime = maxTime;
        this.minTime = minTime;
    }

    // Getters...

    @Override
    public String toString() {
        return String.format(
                "MonitorStats{queries=%d, updates=%d, errors=%d, avgTime=%dms, maxTime=%dms, minTime=%dms}",
                totalQueries, totalUpdates, totalErrors, averageTime, maxTime, minTime
        );
    }
}
