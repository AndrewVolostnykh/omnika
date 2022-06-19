package io.omnika.services.telegram.channel;

import io.omnika.common.ipc.config.EnableIpc;
import io.omnika.common.security.config.EnableSecurity;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableIpc
@EnableSecurity
@SpringBootApplication
public class TelegramChannelApplication {

    public static void main(String[] args) {
        SpringApplication.run(TelegramChannelApplication.class, args);
    }


}
