package io.omnika.services.management.web.config;

import io.omnika.common.rest.services.management.constraints.ValidPassword;
import io.omnika.services.management.service.validation.ValidPasswordValidator;
import javax.validation.Validation;
import javax.validation.Validator;
import org.hibernate.validator.HibernateValidator;
import org.hibernate.validator.HibernateValidatorConfiguration;
import org.hibernate.validator.cfg.ConstraintMapping;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RegisterHibernateValidators {

    @Bean
    public Validator passwordValidator() {
        HibernateValidatorConfiguration validatorConfiguration = Validation.byProvider(HibernateValidator.class).configure();

        ConstraintMapping constraintMapping = validatorConfiguration.createConstraintMapping();
        constraintMapping.constraintDefinition(ValidPassword.class).validatedBy(ValidPasswordValidator.class);

        validatorConfiguration.addMapping(constraintMapping);

        return validatorConfiguration.buildValidatorFactory().getValidator();
    }
}
