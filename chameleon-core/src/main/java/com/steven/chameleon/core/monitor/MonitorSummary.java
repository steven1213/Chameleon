package com.steven.chameleon.core.monitor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.StringJoiner;

/**
 * 监控统计摘要
 * 提供数据库操作的综合统计信息
 */
public class MonitorSummary {
    private static final Logger logger = LoggerFactory.getLogger(MonitorSummary.class);

    private final LocalDateTime startTime;
    private final long totalConnections;
    private final long activeConnections;
    private final long totalQueries;
    private final long totalUpdates;
    private final long totalErrors;
    private final double averageQueryTime;
    private final int slowQueryCount;
    private final long maxQueryTime;
    private final long minQueryTime;
    private final long totalExecutionTime;

    private MonitorSummary(Builder builder) {
        this.startTime = builder.startTime;
        this.totalConnections = builder.totalConnections;
        this.activeConnections = builder.activeConnections;
        this.totalQueries = builder.totalQueries;
        this.totalUpdates = builder.totalUpdates;
        this.totalErrors = builder.totalErrors;
        this.averageQueryTime = builder.averageQueryTime;
        this.slowQueryCount = builder.slowQueryCount;
        this.maxQueryTime = builder.maxQueryTime;
        this.minQueryTime = builder.minQueryTime;
        this.totalExecutionTime = builder.totalExecutionTime;
    }

    /**
     * 获取运行时间
     */
    public Duration getUptime() {
        return Duration.between(startTime, LocalDateTime.now());
    }

    /**
     * 获取总操作数
     */
    public long getTotalOperations() {
        return totalQueries + totalUpdates;
    }

    /**
     * 获取错误率(百分比)
     */
    public double getErrorRate() {
        long total = getTotalOperations();
        return total == 0 ? 0 : (double) totalErrors / total * 100;
    }

    /**
     * 获取每秒操作数
     */
    public double getOperationsPerSecond() {
        long uptimeSeconds = getUptime().getSeconds();
        return uptimeSeconds == 0 ? 0 : (double) getTotalOperations() / uptimeSeconds;
    }

    /**
     * 获取连接使用率(百分比)
     */
    public double getConnectionUtilization() {
        return totalConnections == 0 ? 0 : (double) activeConnections / totalConnections * 100;
    }

    /**
     * 获取简要统计信息
     */
    public String getBriefSummary() {
        return String.format(
                "Uptime: %s, Operations: %d (%.2f/s), Errors: %d (%.2f%%)",
                formatDuration(getUptime()),
                getTotalOperations(),
                getOperationsPerSecond(),
                totalErrors,
                getErrorRate()
        );
    }

    /**
     * 获取详细统计信息
     */
    public String getDetailedSummary() {
        return new StringJoiner("\n")
                .add("=== Database Monitor Summary ===")
                .add(String.format("Start Time: %s", startTime))
                .add(String.format("Uptime: %s", formatDuration(getUptime())))
                .add("--- Operations ---")
                .add(String.format("Total Operations: %d", getTotalOperations()))
                .add(String.format("- Queries: %d", totalQueries))
                .add(String.format("- Updates: %d", totalUpdates))
                .add(String.format("Operations/Second: %.2f", getOperationsPerSecond()))
                .add("--- Performance ---")
                .add(String.format("Average Query Time: %.2f ms", averageQueryTime))
                .add(String.format("Max Query Time: %d ms", maxQueryTime))
                .add(String.format("Min Query Time: %d ms", minQueryTime))
                .add(String.format("Total Execution Time: %d ms", totalExecutionTime))
                .add("--- Errors ---")
                .add(String.format("Total Errors: %d", totalErrors))
                .add(String.format("Error Rate: %.2f%%", getErrorRate()))
                .add("--- Connections ---")
                .add(String.format("Active Connections: %d", activeConnections))
                .add(String.format("Total Connections: %d", totalConnections))
                .add(String.format("Connection Utilization: %.2f%%", getConnectionUtilization()))
                .add("--- Slow Queries ---")
                .add(String.format("Slow Query Count: %d", slowQueryCount))
                .toString();
    }

    /**
     * 格式化持续时间
     */
    private String formatDuration(Duration duration) {
        long days = duration.toDays();
        long hours = duration.toHoursPart();
        long minutes = duration.toMinutesPart();
        long seconds = duration.toSecondsPart();

        StringJoiner joiner = new StringJoiner(" ");
        if (days > 0) joiner.add(days + "d");
        if (hours > 0) joiner.add(hours + "h");
        if (minutes > 0) joiner.add(minutes + "m");
        joiner.add(seconds + "s");

        return joiner.toString();
    }

    // Getters
    public LocalDateTime getStartTime() {
        return startTime;
    }

    public long getTotalConnections() {
        return totalConnections;
    }

    public long getActiveConnections() {
        return activeConnections;
    }

    public long getTotalQueries() {
        return totalQueries;
    }

    public long getTotalUpdates() {
        return totalUpdates;
    }

    public long getTotalErrors() {
        return totalErrors;
    }

    public double getAverageQueryTime() {
        return averageQueryTime;
    }

    public int getSlowQueryCount() {
        return slowQueryCount;
    }

    public long getMaxQueryTime() {
        return maxQueryTime;
    }

    public long getMinQueryTime() {
        return minQueryTime;
    }

    public long getTotalExecutionTime() {
        return totalExecutionTime;
    }

    /**
     * 创建构建器
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * 监控摘要构建器
     */
    public static class Builder {
        private LocalDateTime startTime = LocalDateTime.now();
        private long totalConnections;
        private long activeConnections;
        private long totalQueries;
        private long totalUpdates;
        private long totalErrors;
        private double averageQueryTime;
        private int slowQueryCount;
        private long maxQueryTime;
        private long minQueryTime;
        private long totalExecutionTime;

        public Builder startTime(LocalDateTime startTime) {
            this.startTime = startTime;
            return this;
        }

        public Builder totalConnections(long totalConnections) {
            this.totalConnections = totalConnections;
            return this;
        }

        public Builder activeConnections(long activeConnections) {
            this.activeConnections = activeConnections;
            return this;
        }

        public Builder totalQueries(long totalQueries) {
            this.totalQueries = totalQueries;
            return this;
        }

        public Builder totalUpdates(long totalUpdates) {
            this.totalUpdates = totalUpdates;
            return this;
        }

        public Builder totalErrors(long totalErrors) {
            this.totalErrors = totalErrors;
            return this;
        }

        public Builder averageQueryTime(double averageQueryTime) {
            this.averageQueryTime = averageQueryTime;
            return this;
        }

        public Builder slowQueryCount(int slowQueryCount) {
            this.slowQueryCount = slowQueryCount;
            return this;
        }

        public Builder maxQueryTime(long maxQueryTime) {
            this.maxQueryTime = maxQueryTime;
            return this;
        }

        public Builder minQueryTime(long minQueryTime) {
            this.minQueryTime = minQueryTime;
            return this;
        }

        public Builder totalExecutionTime(long totalExecutionTime) {
            this.totalExecutionTime = totalExecutionTime;
            return this;
        }

        public MonitorSummary build() {
            validate();
            return new MonitorSummary(this);
        }

        /**
         * 验证构建参数
         */
        private void validate() {
            if (startTime == null) {
                throw new IllegalStateException("Start time cannot be null");
            }
            if (totalConnections < activeConnections) {
                throw new IllegalStateException("Total connections cannot be less than active connections");
            }
            if (maxQueryTime < minQueryTime) {
                throw new IllegalStateException("Max query time cannot be less than min query time");
            }
            if (averageQueryTime < 0) {
                throw new IllegalStateException("Average query time cannot be negative");
            }
            if (slowQueryCount < 0) {
                throw new IllegalStateException("Slow query count cannot be negative");
            }
            logger.debug("Monitor summary validation passed");
        }
    }
}