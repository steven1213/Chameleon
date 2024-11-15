package com.steven.chameleon.core.transaction;

import com.steven.chameleon.core.exception.ChameleonException;
import com.steven.chameleon.core.exception.ErrorCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * <template>desc<template>.
 *
 * @author: steven
 * @date: 2024/11/14 17:39
 */
public class DefaultTransactionManager implements TransactionManager {
    private static final Logger logger = LoggerFactory.getLogger(DefaultTransactionManager.class);

    private final DataSource dataSource;
    private final ThreadLocal<Transaction> currentTransaction = new ThreadLocal<>();

    public DefaultTransactionManager(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void beginTransaction() throws SQLException {
        if (isTransactionActive()) {
            throw new ChameleonException("Transaction already active",
                    ErrorCode.TRANSACTION_ALREADY_ACTIVE);
        }

        logger.debug("Beginning new transaction");
        Connection conn = dataSource.getConnection();
        conn.setAutoCommit(false);
        currentTransaction.set(new DefaultTransaction(conn));
    }

    @Override
    public void commit() throws SQLException {
        Transaction transaction = getCurrentTransaction();
        if (transaction == null) {
            throw new ChameleonException("No active transaction",
                    ErrorCode.TRANSACTION_NOT_ACTIVE);
        }

        try {
            logger.debug("Committing transaction");
            transaction.getConnection().commit();
        } finally {
            closeTransaction();
        }
    }

    @Override
    public void rollback() {
        Transaction transaction = getCurrentTransaction();
        if (transaction == null) {
            return;
        }

        try {
            logger.debug("Rolling back transaction");
            transaction.getConnection().rollback();
        } catch (SQLException e) {
            logger.error("Error rolling back transaction", e);
        } finally {
            closeTransaction();
        }
    }

    @Override
    public boolean isTransactionActive() {
        return getCurrentTransaction() != null;
    }

    @Override
    public Transaction getCurrentTransaction() {
        return currentTransaction.get();
    }

    private void closeTransaction() {
        Transaction transaction = currentTransaction.get();
        if (transaction != null) {
            try {
                Connection conn = transaction.getConnection();
                conn.setAutoCommit(true);
                conn.close();
            } catch (SQLException e) {
                logger.error("Error closing transaction", e);
            } finally {
                currentTransaction.remove();
            }
        }
    }
}