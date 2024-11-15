package com.steven.chameleon.monitor.core.model;

import lombok.Data;

@Data
public class MetricsData {
    private String datasourceId;
    private long timestamp;
    private int activeConnections;
    private int totalConnections;
    private long qps;
    private List<SlowQueryInfo> slowQueries;
    // ... 其他指标
}
