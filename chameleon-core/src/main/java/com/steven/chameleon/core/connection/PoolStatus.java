package com.steven.chameleon.core.connection;

/**
 * <template>desc<template>.
 *
 * @author: steven
 * @date: 2024/11/14 16:55
 */
public class PoolStatus {
    private final int activeConnections;
    private final int idleConnections;
    private final int totalConnections;

    public PoolStatus(int activeConnections, int idleConnections, int totalConnections) {
        this.activeConnections = activeConnections;
        this.idleConnections = idleConnections;
        this.totalConnections = totalConnections;
    }

    public int getActiveConnections() {
        return activeConnections;
    }

    public int getIdleConnections() {
        return idleConnections;
    }

    public int getTotalConnections() {
        return totalConnections;
    }
}
