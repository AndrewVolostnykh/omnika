package io.omnika.services.management.web.openapi;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Omnika management service",
                description = "Management of application. This service give us endpoints to "
                        + "create user, create tenant for it and managers, create channels and "
                        + "configuring it")
)
public class OpenApiConfig {

}
