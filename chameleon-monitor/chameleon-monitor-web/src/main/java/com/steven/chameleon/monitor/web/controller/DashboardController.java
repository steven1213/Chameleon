package com.steven.chameleon.monitor.web.controller;

import com.steven.chameleon.monitor.core.model.MetricsData;
import com.steven.chameleon.monitor.web.common.model.Result;
import com.steven.chameleon.monitor.web.vo.DashboardVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {
    
    @GetMapping("/overview")
    public Result<DashboardVO> getOverview() {
        // 返回概览数据
        return null;
    }
    
    @GetMapping("/metrics")
    public Result<List<MetricsData>> getMetrics(
            @RequestParam String datasourceId,
            @RequestParam Long startTime,
            @RequestParam Long endTime) {
        // 返回详细监控数据
        return null;
    }
}
