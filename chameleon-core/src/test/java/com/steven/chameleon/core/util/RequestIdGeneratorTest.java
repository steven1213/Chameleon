package com.steven.chameleon.core.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RequestIdGeneratorTest {

    @Test
    void getOrGenerate() {
        assertEquals(RequestIdGenerator.getOrGenerate(), RequestIdGenerator.getCurrentRequestId());
    }

    @Test
    void generate() {
        String requestId = RequestIdGenerator.generate();
        assertTrue(RequestIdGenerator.isValidRequestId(requestId));
    }

    @Test
    void getCurrentRequestId() {
        assertNotNull(RequestIdGenerator.getCurrentRequestId());
    }

    @Test
    void setRequestId() {
        RequestIdGenerator.setRequestId("123456789");
        assertEquals("123456789", RequestIdGenerator.getCurrentRequestId());
    }

    @Test
    void clear() {
        RequestIdGenerator.clear();
        assertNull(RequestIdGenerator.getCurrentRequestId());
    }

    @Test
    void isValidRequestId() {
        assertTrue(RequestIdGenerator.isValidRequestId("20210831123456789001"));
        assertFalse(RequestIdGenerator.isValidRequestId("2021083112345678900"));
        assertFalse(RequestIdGenerator.isValidRequestId("202108311234567890011"));
        assertFalse(RequestIdGenerator.isValidRequestId("2021083112345678900a"));
        assertFalse(RequestIdGenerator.isValidRequestId("2021083112345678900!"));
    }
}