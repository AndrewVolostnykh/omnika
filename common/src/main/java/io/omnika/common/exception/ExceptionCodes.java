package io.omnika.common.exception;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ExceptionCodes {

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Auth {
        public static final String EMAIL_REQUIRED = "EMAIL_REQUIRED";
        public static final String PASSWORD_REQUIRED = "PASSWORD_REQUIRED";
        public static final String INVALID_EMAIL_PATTERN = "INVALID_EMAIL_PATTERN";
        public static final String INVALID_PASSWORD_PATTERN = "INVALID_PASSWORD_PATTERN";
        public static final String EMAIL_NOT_UNIQ = "EMAIL_NOT_UNIQ";
        public static final String LOGIN_OR_PASSWORD_INCORRECT = "LOGIN_OR_PASSWORD_INCORRECT";
    }

}
