package com.steven.chameleon.monitor.core.alert;

import lombok.Data;

import java.util.List;

@Data
public class AlertRule {
    private String metric;      // 监控指标
    private String operator;    // 操作符 >、<、=
    private double threshold;   // 阈值
    private int duration;       // 持续时间(秒)
    private List<String> channels; // 通知渠道
}
