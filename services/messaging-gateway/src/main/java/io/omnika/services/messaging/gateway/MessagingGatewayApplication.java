package io.omnika.services.messaging.gateway;

import io.omnika.common.ipc.config.EnableIpc;
import io.omnika.common.rest.config.EnableOpenApi;
import io.omnika.common.security.config.EnableSecurity;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableIpc
@EnableSecurity
@EnableOpenApi
@SpringBootApplication
public class MessagingGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(MessagingGatewayApplication.class, args);
    }
}

