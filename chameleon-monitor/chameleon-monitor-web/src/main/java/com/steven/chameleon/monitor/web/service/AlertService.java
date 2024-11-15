package com.steven.chameleon.monitor.web.service;

import com.steven.chameleon.monitor.core.alert.AlertRule;
import com.steven.chameleon.monitor.core.model.MetricsData;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class AlertService {
    
    @Async
    public void checkAlertRules(MetricsData data) {
        // 检查告警规则
        // 触发告警通知
    }
    
    public void updateAlertRule(AlertRule rule) {
        // 更新告警规则
    }
}
