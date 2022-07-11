package io.omnika.services.management.utils;

import io.omnika.services.management.core.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DbFiller implements ApplicationRunner {

    private final UserService userService;
    @Value("${security.sysadmin.email}")
    private String sysadminEmail;
    @Value("${security.sysadmin.password}")
    private String sysadminPassword;

    @Override
    public void run(ApplicationArguments args) throws Exception {
    }

}
