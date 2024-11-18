package com.steven.chameleon.core.util;

import org.slf4j.MDC;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ThreadLocalRandom;

/**
 * RequestId生成器
 * 用于生成和管理请求ID，支持分布式环境下的请求追踪
 */
public final class RequestIdGenerator {
    
    private RequestIdGenerator() {
        throw new UnsupportedOperationException("Utility class");
    }

    /** MDC中RequestId的key */
    public static final String REQUEST_ID_KEY = "requestId";
    
    /** 请求头中RequestId的key */
    public static final String REQUEST_ID_HEADER = "X-Request-Id";
    
    /** 时间戳格式化器 - 17位 */
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
    
    /** 机器标识位数 - 2位 */
    private static final int MACHINE_BITS = 5;
    private static final int MAX_MACHINE_NUM = ~(-1 << MACHINE_BITS);
    private static final int MACHINE_ID = 1;
    
    /** 随机数位数 - 6位 */
    private static final int RANDOM_BITS = 6;
    private static final int MAX_RANDOM = (int) Math.pow(10, RANDOM_BITS);  // 1_000_000
    
    /** ID总长度 - 25位 */
    private static final int TOTAL_LENGTH = 25;
    
    /**
     * 从MDC获取或生成新的requestId
     *
     * @return requestId
     */
    public static String getOrGenerate() {
        String requestId = getCurrentRequestId();
        return requestId != null ? requestId : generate();
    }
    
    /**
     * 生成新的requestId
     * 格式: 时间戳(17位) + 机器标识(2位) + 随机数(6位) = 25位
     * 示例: 20240319152132123 01 123456
     *
     * @return 新生成的requestId
     * @throws IllegalStateException 如果生成的ID长度不符合预期
     */
    public static String generate() {
        // 1. 时间戳部分 (17位)
        String timestamp = LocalDateTime.now().format(FORMATTER);
        
        // 2. 机器标识部分（2位，补零）
        String machineId = String.format("%02d", MACHINE_ID & MAX_MACHINE_NUM);
        
        // 3. 随机数部分（6位，补零）
        String random = String.format("%0" + RANDOM_BITS + "d", 
            ThreadLocalRandom.current().nextInt(MAX_RANDOM));
        
        // 4. 组合
        String requestId = timestamp + machineId + random;
        
        // 5. 验证长度
        if (requestId.length() != TOTAL_LENGTH) {
            throw new IllegalStateException(
                String.format("Generated ID length mismatch. Expected: %d, Actual: %d", 
                    TOTAL_LENGTH, requestId.length())
            );
        }
        
        return requestId;
    }
    
    /**
     * 获取当前请求的requestId
     *
     * @return 当前requestId，如果不存在返回null
     */
    public static String getCurrentRequestId() {
        return MDC.get(REQUEST_ID_KEY);
    }
    
    /**
     * 设置requestId到MDC
     *
     * @param requestId 要设置的requestId
     */
    public static void setRequestId(String requestId) {
        if (requestId != null && !requestId.isEmpty()) {
            MDC.put(REQUEST_ID_KEY, requestId);
        }
    }
    
    /**
     * 清除MDC中的requestId
     */
    public static void clear() {
        MDC.remove(REQUEST_ID_KEY);
    }
    
    /**
     * 验证requestId格式是否正确
     *
     * @param requestId 要验证的requestId
     * @return 是否是有效的requestId
     */
    public static boolean isValidRequestId(String requestId) {
        if (requestId == null || requestId.length() != 25) {
            return false;
        }
        try {
            // 验证时间戳部分
            String timestampStr = requestId.substring(0, 17);
            LocalDateTime.parse(timestampStr, FORMATTER);
            
            // 验证机器标识部分
            int machineId = Integer.parseInt(requestId.substring(17, 19));
            if (machineId > MAX_MACHINE_NUM) {
                return false;
            }
            
            // 验证随机数部分
            Integer.parseInt(requestId.substring(19));
            
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}