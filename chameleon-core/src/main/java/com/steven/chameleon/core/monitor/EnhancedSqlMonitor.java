package com.steven.chameleon.core.monitor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.LongAdder;
import java.util.stream.Collectors;

/**
 * <template>desc<template>.
 *
 * @author: steven
 * @date: 2024/11/15 10:59
 */



/**
 * 增强的SQL监控
 */
public class EnhancedSqlMonitor extends SqlMonitor {
    private static final Logger logger = LoggerFactory.getLogger(EnhancedSqlMonitor.class);

    private final Map<String, TableStats> tableStats = new ConcurrentHashMap<>();
    private final Map<String, QueryStats> slowQueries = new ConcurrentHashMap<>();
    private final long slowQueryThreshold;
    private final int maxSlowQueries;
    private final LongAdder totalConnections = new LongAdder();
    private final LongAdder activeConnections = new LongAdder();
    private final LocalDateTime startTime;

    public EnhancedSqlMonitor(long slowQueryThreshold, int maxSlowQueries) {
        this.slowQueryThreshold = slowQueryThreshold;
        this.maxSlowQueries = maxSlowQueries;
        this.startTime = LocalDateTime.now();
        logger.info("EnhancedSqlMonitor initialized with slowQueryThreshold={} ms, maxSlowQueries={}",
                slowQueryThreshold, maxSlowQueries);
    }

    /**
     * 记录表访问
     */
    public void recordTableAccess(String tableName, String operation, long timeMillis) {
        tableStats.computeIfAbsent(tableName, k -> new TableStats())
                .record(operation, timeMillis);
        logger.debug("Table access recorded: {} - {} - {} ms", tableName, operation, timeMillis);
    }

    /**
     * 记录慢查询
     */
    public void recordSlowQuery(String sql, long timeMillis, Object[] params) {
        if (timeMillis > slowQueryThreshold) {
            String normalizedSql = normalizeSql(sql);
            QueryStats stats = slowQueries.computeIfAbsent(normalizedSql, k -> new QueryStats(sql, params));
            stats.record(timeMillis, params);

            if (slowQueries.size() > maxSlowQueries) {
                removeShortestSlowQuery();
            }

            logger.warn("Slow query detected: {} ms, SQL: {}", timeMillis, sql);
        }
    }

    /**
     * 记录连接获取
     */
    public void recordConnectionAcquired() {
        totalConnections.increment();
        activeConnections.increment();
        logger.debug("Connection acquired, active: {}, total: {}",
                activeConnections.sum(), totalConnections.sum());
    }

    /**
     * 记录连接释放
     */
    public void recordConnectionReleased() {
        activeConnections.decrement();
        logger.debug("Connection released, active: {}", activeConnections.sum());
    }

    /**
     * 获取表统计信息
     */
    public List<TableStatistics> getTableStatistics() {
        return tableStats.entrySet().stream()
                .map(entry -> new TableStatistics(
                        entry.getKey(),
                        entry.getValue().getSelectCount(),
                        entry.getValue().getInsertCount(),
                        entry.getValue().getUpdateCount(),
                        entry.getValue().getDeleteCount(),
                        entry.getValue().getTotalTime(),
                        entry.getValue().getAverageTime()
                ))
                .collect(Collectors.toList());
    }

    /**
     * 获取慢查询统计信息
     */
    public List<SlowQueryStatistics> getSlowQueryStatistics() {
        return slowQueries.values().stream()
                .map(stats -> new SlowQueryStatistics(
                        stats.getSql(),
                        stats.getExecutionCount(),
                        stats.getTotalTime(),
                        stats.getAverageTime(),
                        stats.getMaxTime(),
                        stats.getLastParams(),
                        stats.getLastExecutionTime()
                ))
                .sorted((a, b) -> Long.compare(b.getMaxTime(), a.getMaxTime()))
                .collect(Collectors.toList());
    }

    /**
     * 获取监控摘要
     */
    @Override
    public MonitorSummary getMonitorSummary() {
        BasicStats basicStats = getBasicStats();
        return MonitorSummary.builder()
                .startTime(startTime)
                .totalConnections(totalConnections.sum())
                .activeConnections(activeConnections.sum())
                .totalQueries(basicStats.getTotalQueries())
                .totalUpdates(basicStats.getTotalUpdates())
                .totalErrors(basicStats.getTotalErrors())
                .averageQueryTime(basicStats.getAverageQueryTime())
                .slowQueryCount(slowQueries.size())
                .maxQueryTime(basicStats.getMaxQueryTime())
                .minQueryTime(basicStats.getMinQueryTime())
                .totalExecutionTime(basicStats.getTotalExecutionTime())
                .build();
    }

    /**
     * 重置统计信息
     */
    @Override
    public void reset() {
        super.reset();
        tableStats.clear();
        slowQueries.clear();
        totalConnections.reset();
        activeConnections.reset();
        logger.info("Enhanced monitor statistics reset");
    }

    /**
     * 规范化SQL语句
     */
    private String normalizeSql(String sql) {
        return sql.replaceAll("'[^']*'", "?")
                .replaceAll("\\d+", "?")
                .trim();
    }

    /**
     * 移除执行时间最短的慢查询
     */
    private void removeShortestSlowQuery() {
        String shortestQuery = slowQueries.entrySet().stream()
                .min((a, b) -> Long.compare(a.getValue().getMaxTime(), b.getValue().getMaxTime()))
                .map(Map.Entry::getKey)
                .orElse(null);

        if (shortestQuery != null) {
            slowQueries.remove(shortestQuery);
            logger.debug("Removed shortest slow query: {}", shortestQuery);
        }
    }
}

/**
 * 表统计信息
 */
class TableStats {
    private final LongAdder selectCount = new LongAdder();
    private final LongAdder insertCount = new LongAdder();
    private final LongAdder updateCount = new LongAdder();
    private final LongAdder deleteCount = new LongAdder();
    private final LongAdder totalTime = new LongAdder();

    public void record(String operation, long timeMillis) {
        switch (operation.toUpperCase()) {
            case "SELECT":
                selectCount.increment();
                break;
            case "INSERT":
                insertCount.increment();
                break;
            case "UPDATE":
                updateCount.increment();
                break;
            case "DELETE":
                deleteCount.increment();
                break;
        }
        totalTime.add(timeMillis);
    }

    public long getSelectCount() {
        return selectCount.sum();
    }

    public long getInsertCount() {
        return insertCount.sum();
    }

    public long getUpdateCount() {
        return updateCount.sum();
    }

    public long getDeleteCount() {
        return deleteCount.sum();
    }

    public long getTotalTime() {
        return totalTime.sum();
    }

    public double getAverageTime() {
        long total = getSelectCount() + getInsertCount() + getUpdateCount() + getDeleteCount();
        return total == 0 ? 0 : (double) getTotalTime() / total;
    }
}

/**
 * 查询统计信息
 */
class QueryStats {
    private final String sql;
    private final LongAdder executionCount = new LongAdder();
    private final LongAdder totalTime = new LongAdder();
    private volatile long maxTime = 0;
    private volatile LocalDateTime lastExecutionTime;
    private volatile Object[] lastParams;

    public QueryStats(String sql, Object[] params) {
        this.sql = sql;
        this.lastParams = params;
        this.lastExecutionTime = LocalDateTime.now();
    }

    public void record(long timeMillis, Object[] params) {
        executionCount.increment();
        totalTime.add(timeMillis);
        updateMaxTime(timeMillis);
        this.lastExecutionTime = LocalDateTime.now();
        this.lastParams = params;
    }

    private void updateMaxTime(long timeMillis) {
        if (timeMillis > maxTime) {
            synchronized (this) {
                if (timeMillis > maxTime) {
                    maxTime = timeMillis;
                }
            }
        }
    }

    public String getSql() {
        return sql;
    }

    public long getExecutionCount() {
        return executionCount.sum();
    }

    public long getTotalTime() {
        return totalTime.sum();
    }

    public double getAverageTime() {
        return getExecutionCount() == 0 ? 0 : (double) getTotalTime() / getExecutionCount();
    }

    public long getMaxTime() {
        return maxTime;
    }

    public Object[] getLastParams() {
        return lastParams;
    }

    public LocalDateTime getLastExecutionTime() {
        return lastExecutionTime;
    }
}

/**
 * 表统计信息
 */
class TableStatistics {
    private final String tableName;
    private final long selectCount;
    private final long insertCount;
    private final long updateCount;
    private final long deleteCount;
    private final long totalTime;
    private final double averageTime;

    public TableStatistics(String tableName, long selectCount, long insertCount,
                           long updateCount, long deleteCount, long totalTime,
                           double averageTime) {
        this.tableName = tableName;
        this.selectCount = selectCount;
        this.insertCount = insertCount;
        this.updateCount = updateCount;
        this.deleteCount = deleteCount;
        this.totalTime = totalTime;
        this.averageTime = averageTime;
    }

    public String getTableName() {
        return tableName;
    }

    public long getSelectCount() {
        return selectCount;
    }

    public long getInsertCount() {
        return insertCount;
    }

    public long getUpdateCount() {
        return updateCount;
    }

    public long getDeleteCount() {
        return deleteCount;
    }

    public long getTotalTime() {
        return totalTime;
    }

    public double getAverageTime() {
        return averageTime;
    }
}

/**
 * 慢查询统计信息
 */
class SlowQueryStatistics {
    private final String sql;
    private final long executionCount;
    private final long totalTime;
    private final double averageTime;
    private final long maxTime;
    private final Object[] lastParams;
    private final LocalDateTime lastExecutionTime;

    public SlowQueryStatistics(String sql, long executionCount, long totalTime,
                               double averageTime, long maxTime, Object[] lastParams,
                               LocalDateTime lastExecutionTime) {
        this.sql = sql;
        this.executionCount = executionCount;
        this.totalTime = totalTime;
        this.averageTime = averageTime;
        this.maxTime = maxTime;
        this.lastParams = lastParams;
        this.lastExecutionTime = lastExecutionTime;
    }

    public String getSql() {
        return sql;
    }

    public long getExecutionCount() {
        return executionCount;
    }

    public long getTotalTime() {
        return totalTime;
    }

    public double getAverageTime() {
        return averageTime;
    }

    public long getMaxTime() {
        return maxTime;
    }

    public Object[] getLastParams() {
        return lastParams;
    }

    public LocalDateTime getLastExecutionTime() {
        return lastExecutionTime;
    }
}
