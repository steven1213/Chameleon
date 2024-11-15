package com.steven.chameleon.spring.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(ChameleonProperties.class)
@ComponentScan("com.steven.chameleon.spring")
public class ChameleonAutoConfiguration {
    
    private final ChameleonProperties properties;
    
    public ChameleonAutoConfiguration(ChameleonProperties properties) {
        this.properties = properties;
    }
}
