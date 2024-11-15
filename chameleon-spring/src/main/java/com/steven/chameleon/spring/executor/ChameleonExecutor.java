package com.steven.chameleon.spring.executor;

import com.steven.chameleon.spring.model.ChameleonCommand;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Component
public class ChameleonExecutor {
    
    private final ExecutorService executorService;
    
    public ChameleonExecutor() {
        this.executorService = Executors.newFixedThreadPool(
            Runtime.getRuntime().availableProcessors()
        );
    }
    
    public void execute(ChameleonCommand command, Runnable task) {
        executorService.execute(() -> {
            try {
                command.setStatus(ChameleonCommand.CommandStatus.EXECUTING);
                task.run();
                command.setStatus(ChameleonCommand.CommandStatus.COMPLETED);
            } catch (Exception e) {
                command.setStatus(ChameleonCommand.CommandStatus.FAILED);
                throw new RuntimeException("Failed to execute command", e);
            }
        });
    }
    
    public void shutdown() {
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}
