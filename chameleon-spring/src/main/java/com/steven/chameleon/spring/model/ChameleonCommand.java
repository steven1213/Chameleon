package com.steven.chameleon.spring.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
public class ChameleonCommand implements Serializable {
    
    private String id;
    private String command;
    private CommandStatus status;
    private LocalDateTime createTime;
    private LocalDateTime executeTime;
    
    public enum CommandStatus {
        PENDING, EXECUTING, COMPLETED, FAILED
    }
}
