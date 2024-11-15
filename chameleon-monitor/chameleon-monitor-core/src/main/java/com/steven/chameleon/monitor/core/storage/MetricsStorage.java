package com.steven.chameleon.monitor.core.storage;

import com.steven.chameleon.monitor.core.model.MetricsData;

import java.util.List;

public interface MetricsStorage {
    void save(MetricsData data);
    List<MetricsData> query(String datasourceId, long startTime, long endTime);
    void cleanup(int retentionDays);
}
