package io.omnika.common.rest.services.management.constraints;

import io.omnika.common.exceptions.ExceptionCodes.Validation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = {})
public @interface ValidPassword {
    String message() default "{exceptionCode}";

    String exceptionCode() default Validation.INVALID_PASSWORD_PATTERN;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
