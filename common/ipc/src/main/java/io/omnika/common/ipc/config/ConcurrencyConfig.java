package io.omnika.common.ipc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@Configuration
// TODO: move to another module
public class ConcurrencyConfig {

    @Bean(destroyMethod = "shutdownNow")
    public ExecutorService executor() {
        return Executors.newWorkStealingPool(Runtime.getRuntime().availableProcessors() * 2);
    }

    @Bean
    public ScheduledExecutorService scheduler() {
        return Executors.newSingleThreadScheduledExecutor();
    }

}
