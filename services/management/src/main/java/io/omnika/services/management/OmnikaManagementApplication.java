package io.omnika.services.management;

import io.omnika.common.security.config.EnableSecurity;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableSecurity
@SpringBootApplication
public class OmnikaManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(OmnikaManagementApplication.class, args);
    }
}
