package com.steven.chameleon.spring.service.impl;

import com.steven.chameleon.spring.config.ChameleonProperties;
import com.steven.chameleon.spring.exception.ChameleonException;
import com.steven.chameleon.spring.service.ChameleonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class DefaultChameleonService implements ChameleonService {
    
    private static final Logger logger = LoggerFactory.getLogger(DefaultChameleonService.class);
    
    private final ChameleonProperties properties;
    private final AtomicBoolean running = new AtomicBoolean(false);
    
    public DefaultChameleonService(ChameleonProperties properties) {
        this.properties = properties;
    }
    
    @PostConstruct
    @Override
    public void initialize() {
        if (running.compareAndSet(false, true)) {
            logger.info("Initializing Chameleon service with properties: {}", properties);
            // TODO: 添加初始化逻辑
        }
    }
    
    @Override
    public void execute(String command) {
        if (!running.get()) {
            throw new ChameleonException("Service is not running");
        }
        logger.debug("Executing command: {}", command);
        // TODO: 实现命令执行逻辑
    }
    
    @PreDestroy
    @Override
    public void shutdown() {
        if (running.compareAndSet(true, false)) {
            logger.info("Shutting down Chameleon service");
            // TODO: 添加清理逻辑
        }
    }
    
    @Override
    public boolean isRunning() {
        return running.get();
    }
}
