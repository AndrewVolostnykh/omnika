package io.omnika.service.channel.instagram;

import io.omnika.common.ipc.config.EnableIpc;
import io.omnika.common.security.config.EnableSecurity;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@EnableIpc
@EnableSecurity
@SpringBootApplication
public class InstagramChannelApplication {

    public static void main(String[] args) {

        SpringApplication.run(InstagramChannelApplication.class, args);
    }

    @Bean
    public ExecutorService workStealingPool() {
        return Executors.newWorkStealingPool(Runtime.getRuntime().availableProcessors() * 2);
    }

}
