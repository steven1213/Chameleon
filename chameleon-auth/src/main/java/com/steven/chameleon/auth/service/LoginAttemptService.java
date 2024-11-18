package com.steven.chameleon.auth.service;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class LoginAttemptService {
    
    private final Cache<String, Integer> attemptsCache;
    
    @Value("${chameleon.auth.security.login.max-attempts:5}")
    private int maxAttempts;
    
    @Value("${chameleon.auth.security.login.block-duration:30}")
    private int blockDurationMinutes;
    
    public LoginAttemptService() {
        attemptsCache = Caffeine.newBuilder()
                .expireAfterWrite(30, TimeUnit.MINUTES)
                .build();
    }
    
    public void loginSucceeded(String key) {
        attemptsCache.invalidate(key);
    }
    
    public void loginFailed(String key) {
        int attempts = attemptsCache.get(key, k -> 0);
        attemptsCache.put(key, attempts + 1);
    }
    
    public boolean isBlocked(String key) {
        return attemptsCache.get(key, k -> 0) >= maxAttempts;
    }
}
