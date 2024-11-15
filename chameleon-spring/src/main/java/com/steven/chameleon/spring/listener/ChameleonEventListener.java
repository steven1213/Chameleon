package com.steven.chameleon.spring.listener;

import com.steven.chameleon.spring.event.ChameleonEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class ChameleonEventListener implements ApplicationListener<ChameleonEvent> {
    
    private static final Logger logger = LoggerFactory.getLogger(ChameleonEventListener.class);
    
    @Override
    public void onApplicationEvent(ChameleonEvent event) {
        logger.info("Received chameleon event: action={}, data={}", 
            event.getAction(), event.getData());
    }
}
