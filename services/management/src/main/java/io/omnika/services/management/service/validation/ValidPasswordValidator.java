package io.omnika.services.management.service.validation;

import io.omnika.common.rest.services.management.constraints.ValidPassword;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.StringUtils;
import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.LengthRule;
import org.passay.PasswordData;
import org.passay.PasswordValidator;

public class ValidPasswordValidator implements ConstraintValidator<ValidPassword, Object> {

    private static final PasswordValidator passwordValidator = new PasswordValidator(
            new LengthRule(8, 24),
            new CharacterRule(EnglishCharacterData.Digit),
            new CharacterRule(EnglishCharacterData.Alphabetical),
            new CharacterRule(EnglishCharacterData.UpperCase),
            new CharacterRule(EnglishCharacterData.LowerCase)
    );

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
        if (!(value instanceof String) || StringUtils.isBlank(((String) value))) {
            return false;
        }

        return passwordValidator.validate(new PasswordData((String) value)).isValid();
    }
}
