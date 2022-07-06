package io.omnika.services.management;

import io.omnika.common.ipc.config.EnableIpc;
import io.omnika.common.security.config.EnableSecurity;
import io.omnika.common.utils.config.EnableUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableIpc
@EnableUtils
@EnableSecurity
@SpringBootApplication
public class ManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(ManagementApplication.class, args);
    }

}

