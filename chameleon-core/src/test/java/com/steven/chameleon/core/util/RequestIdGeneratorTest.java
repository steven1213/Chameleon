package com.steven.chameleon.core.util;

import org.junit.jupiter.api.Test;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.*;

class RequestIdGeneratorTest {

    @Test
    void testGenerate() {
        String requestId = RequestIdGenerator.generate();
        
        // 验证长度
        assertEquals(25, requestId.length());
        
        // 验证格式
        assertTrue(RequestIdGenerator.isValidRequestId(requestId));
    }

    @Test
    void testGetOrGenerate() {
        // 应该生成新的requestId
        String requestId = RequestIdGenerator.getOrGenerate();
        assertNotNull(requestId);
        assertEquals(25, requestId.length());
        assertTrue(RequestIdGenerator.isValidRequestId(requestId));
    }

    @Test
    void testIsValidRequestId() {
        // 有效的requestId
        String validId = RequestIdGenerator.generate();
        assertTrue(RequestIdGenerator.isValidRequestId(validId));

        // 无效的cases
        assertFalse(RequestIdGenerator.isValidRequestId(null));
        assertFalse(RequestIdGenerator.isValidRequestId(""));
        assertFalse(RequestIdGenerator.isValidRequestId("123")); // 长度不对
        assertFalse(RequestIdGenerator.isValidRequestId("12345678901234567XX123456")); // 格式不对
    }

    @Test
    void testUniqueness() throws InterruptedException {
        int threadCount = 100;
        int iterationsPerThread = 100;
        Set<String> ids = new HashSet<>();
        CountDownLatch latch = new CountDownLatch(threadCount);
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);

        for (int i = 0; i < threadCount; i++) {
            executor.execute(() -> {
                try {
                    for (int j = 0; j < iterationsPerThread; j++) {
                        String id = RequestIdGenerator.generate();
                        synchronized (ids) {
                            assertTrue(ids.add(id), "Duplicate ID generated: " + id);
                        }
                    }
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();
        executor.shutdown();
        assertEquals(threadCount * iterationsPerThread, ids.size());
    }
}