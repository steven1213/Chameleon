package com.steven.chameleon.monitor.core.collector;

import com.steven.chameleon.monitor.core.model.MetricsData;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Queue;

@Component
public class MetricsCollector {
    private final Queue<MetricsData> metricsBuffer;
    
    @Async
    public void collect(MetricsData data) {
        // 异步收集指标数据
        metricsBuffer.offer(data);
    }
    
    @Scheduled(fixedRate = 5000)
    public void flush() {
        // 定期将数据写入存储
    }
}
