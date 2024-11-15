package com.steven.chameleon.core.transaction;

import java.util.ArrayList;
import java.util.List;

/**
 * <template>desc<template>.
 *
 * @author: steven
 * @date: 2024/11/14 17:40
 */
public class TransactionSynchronizationManager {
    private static final ThreadLocal<List<TransactionSynchronization>> synchronizations =
            new ThreadLocal<>();

    /**
     * 注册事务同步
     */
    public static void registerSynchronization(TransactionSynchronization synchronization) {
        List<TransactionSynchronization> syncList = synchronizations.get();
        if (syncList == null) {
            syncList = new ArrayList<>();
            synchronizations.set(syncList);
        }
        syncList.add(synchronization);
    }

    /**
     * 触发事务提交前的同步
     */
    public static void triggerBeforeCommit() {
        List<TransactionSynchronization> syncList = synchronizations.get();
        if (syncList != null) {
            for (TransactionSynchronization sync : syncList) {
                sync.beforeCommit();
            }
        }
    }

    /**
     * 触发事务提交后的同步
     */
    public static void triggerAfterCommit() {
        List<TransactionSynchronization> syncList = synchronizations.get();
        if (syncList != null) {
            for (TransactionSynchronization sync : syncList) {
                sync.afterCommit();
            }
        }
    }

    /**
     * 触发事务完成后的同步
     */
    public static void triggerAfterCompletion(TransactionStatus status) {
        List<TransactionSynchronization> syncList = synchronizations.get();
        if (syncList != null) {
            for (TransactionSynchronization sync : syncList) {
                sync.afterCompletion(status);
            }
        }
    }

    /**
     * 清理同步
     */
    public static void clear() {
        synchronizations.remove();
    }
}
