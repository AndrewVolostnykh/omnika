package io.omnika.service.instagram.channel.web.config;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExecutorsConfiguration {

    @Bean
    public ExecutorService workStealingPool() {
        return Executors.newWorkStealingPool(Runtime.getRuntime().availableProcessors() * 2);
    }

}
