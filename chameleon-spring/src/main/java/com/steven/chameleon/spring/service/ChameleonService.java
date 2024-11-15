package com.steven.chameleon.spring.service;

public interface ChameleonService {
    
    void initialize();
    
    void execute(String command);
    
    void shutdown();
    
    boolean isRunning();
}
