package com.steven.chameleon.core.monitor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;

/**
 * <template>基础SQL监控<template>.
 *
 * @author: steven
 * @date: 2024/11/15 10:54
 */


/**
 * 基础SQL监控
 */
public class SqlMonitor {
    private static final Logger logger = LoggerFactory.getLogger(SqlMonitor.class);

    protected final LongAdder totalQueries = new LongAdder();
    protected final LongAdder totalUpdates = new LongAdder();
    protected final LongAdder totalErrors = new LongAdder();
    protected final LongAdder queryTimeTotal = new LongAdder();
    protected final AtomicLong maxQueryTime = new AtomicLong(0);
    protected final AtomicLong minQueryTime = new AtomicLong(Long.MAX_VALUE);
    protected final LocalDateTime startTime;

    public SqlMonitor() {
        this.startTime = LocalDateTime.now();
    }

    /**
     * 记录查询
     */
    public void recordQuery(long timeMillis) {
        totalQueries.increment();
        recordTime(timeMillis);
        logger.debug("Query recorded: {} ms", timeMillis);
    }

    /**
     * 记录更新
     */
    public void recordUpdate(long timeMillis) {
        totalUpdates.increment();
        recordTime(timeMillis);
        logger.debug("Update recorded: {} ms", timeMillis);
    }

    /**
     * 记录错误
     */
    public void recordError() {
        totalErrors.increment();
        logger.debug("Error recorded");
    }

    /**
     * 记录执行时间
     */
    protected void recordTime(long timeMillis) {
        queryTimeTotal.add(timeMillis);
        updateMaxTime(timeMillis);
        updateMinTime(timeMillis);
    }

    /**
     * 更新最大执行时间
     */
    private void updateMaxTime(long timeMillis) {
        while (true) {
            long current = maxQueryTime.get();
            if (timeMillis <= current || maxQueryTime.compareAndSet(current, timeMillis)) {
                break;
            }
        }
    }

    /**
     * 更新最小执行时间
     */
    private void updateMinTime(long timeMillis) {
        while (true) {
            long current = minQueryTime.get();
            if (timeMillis >= current || minQueryTime.compareAndSet(current, timeMillis)) {
                break;
            }
        }
    }

    /**
     * 重置统计信息
     */
    public void reset() {
        totalQueries.reset();
        totalUpdates.reset();
        totalErrors.reset();
        queryTimeTotal.reset();
        maxQueryTime.set(0);
        minQueryTime.set(Long.MAX_VALUE);
        logger.info("Monitor statistics reset");
    }

    /**
     * 获取基础统计信息
     */
    protected BasicStats getBasicStats() {
        return new BasicStats(
                totalQueries.sum(),
                totalUpdates.sum(),
                totalErrors.sum(),
                queryTimeTotal.sum(),
                maxQueryTime.get(),
                minQueryTime.get()
        );
    }

    /**
     * 获取监控摘要
     */
    public MonitorSummary getMonitorSummary() {
        BasicStats stats = getBasicStats();
        return MonitorSummary.builder()
                .startTime(startTime)
                .totalQueries(stats.getTotalQueries())
                .totalUpdates(stats.getTotalUpdates())
                .totalErrors(stats.getTotalErrors())
                .averageQueryTime(stats.getAverageQueryTime())
                .maxQueryTime(stats.getMaxQueryTime())
                .minQueryTime(stats.getMinQueryTime())
                .totalExecutionTime(stats.getTotalExecutionTime())
                .build();
    }
}
