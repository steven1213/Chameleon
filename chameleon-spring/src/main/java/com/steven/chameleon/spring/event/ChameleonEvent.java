package com.steven.chameleon.spring.event;

import org.springframework.context.ApplicationEvent;

public class ChameleonEvent extends ApplicationEvent {
    
    private final String action;
    private final String data;
    
    public ChameleonEvent(Object source, String action, String data) {
        super(source);
        this.action = action;
        this.data = data;
    }
    
    public String getAction() {
        return action;
    }
    
    public String getData() {
        return data;
    }
}
