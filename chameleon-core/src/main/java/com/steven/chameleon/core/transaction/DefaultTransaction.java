package com.steven.chameleon.core.transaction;

import com.steven.chameleon.core.exception.ChameleonException;
import com.steven.chameleon.core.exception.ErrorCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * <template>desc<template>.
 *
 * @author: steven
 * @date: 2024/11/14 17:40
 */
public class DefaultTransaction implements Transaction {
    private static final Logger logger = LoggerFactory.getLogger(DefaultTransaction.class);

    private final Connection connection;
    private TransactionStatus status;
    private IsolationLevel isolationLevel;

    public DefaultTransaction(Connection connection) {
        this.connection = connection;
        this.status = TransactionStatus.ACTIVE;
        try {
            this.isolationLevel = IsolationLevel.valueOf(connection.getTransactionIsolation());
        } catch (SQLException e) {
            this.isolationLevel = IsolationLevel.DEFAULT;
        }
    }

    @Override
    public void commit() throws ChameleonException {
        if (status != TransactionStatus.ACTIVE) {
            throw new ChameleonException("Transaction is not active",
                    ErrorCode.TRANSACTION_ERROR);
        }

        try {
            if (status == TransactionStatus.MARKED_ROLLBACK) {
                rollback();
            } else {
                connection.commit();
                status = TransactionStatus.COMMITTED;
            }
        } catch (SQLException e) {
            throw new ChameleonException("Failed to commit transaction",
                    ErrorCode.TRANSACTION_ERROR, e);
        }
    }

    @Override
    public void rollback() throws ChameleonException {
        if (status == TransactionStatus.COMMITTED ||
                status == TransactionStatus.ROLLED_BACK) {
            throw new ChameleonException("Transaction already completed",
                    ErrorCode.TRANSACTION_ERROR);
        }

        try {
            connection.rollback();
            status = TransactionStatus.ROLLED_BACK;
        } catch (SQLException e) {
            throw new ChameleonException("Failed to rollback transaction",
                    ErrorCode.TRANSACTION_ERROR, e);
        }
    }

    @Override
    public TransactionStatus getStatus() {
        return status;
    }

    @Override
    public void setIsolationLevel(IsolationLevel level) throws ChameleonException {
        try {
            connection.setTransactionIsolation(level.getLevel());
            this.isolationLevel = level;
        } catch (SQLException e) {
            throw new ChameleonException("Failed to set isolation level",
                    ErrorCode.TRANSACTION_ERROR, e);
        }
    }

    @Override
    public IsolationLevel getIsolationLevel() {
        return isolationLevel;
    }

    @Override
    public void setRollbackOnly() {
        this.status = TransactionStatus.MARKED_ROLLBACK;
    }

    @Override
    public boolean isActive() {
        return status == TransactionStatus.ACTIVE;
    }

    @Override
    public boolean isCompleted() {
        return status == TransactionStatus.COMMITTED ||
                status == TransactionStatus.ROLLED_BACK;
    }

    @Override
    public void close() throws Exception {
        try {
            if (status == TransactionStatus.ACTIVE) {
                rollback();
            }

            if (!connection.getAutoCommit()) {
                connection.setAutoCommit(true);
            }
            connection.close();
        } catch (SQLException e) {
            throw new ChameleonException("Failed to close transaction",
                    ErrorCode.TRANSACTION_ERROR, e);
        }
    }

    /**
     * 获取事务的连接
     */
    public Connection getConnection() {
        return connection;
    }
}
