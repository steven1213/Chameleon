package com.steven.chameleon.monitor.web.vo;

import lombok.Data;

/**
 * <template>desc<template>.
 *
 * @author: steven
 * @date: 2024/11/15 16:15
 */
@Data
public class DashboardVO {
    // 添加你需要在概览中展示的字段
    // 例如：
    private Integer totalConnections;
    private Integer activeConnections;

    // 生成 getter/setter
}
