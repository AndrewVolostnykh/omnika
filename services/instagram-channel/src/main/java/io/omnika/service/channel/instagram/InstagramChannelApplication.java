package io.omnika.service.channel.instagram;

import io.omnika.common.ipc.config.EnableIpc;
import io.omnika.common.security.config.EnableSecurity;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableIpc
@EnableSecurity
@SpringBootApplication
public class InstagramChannelApplication {

    public static void main(String[] args) {
        SpringApplication.run(InstagramChannelApplication.class, args);
    }

}
