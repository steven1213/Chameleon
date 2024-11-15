package com.steven.chameleon.spring.util;

import com.steven.chameleon.spring.exception.ChameleonException;
import org.springframework.util.StringUtils;

public class ChameleonUtil {
    
    public static void validateHost(String host) {
        if (!StringUtils.hasText(host)) {
            throw new ChameleonException("Host cannot be empty");
        }
    }
    
    public static void validatePort(int port) {
        if (port <= 0 || port > 65535) {
            throw new ChameleonException("Invalid port number: " + port);
        }
    }
    
    private ChameleonUtil() {
        throw new IllegalStateException("Utility class");
    }
}
