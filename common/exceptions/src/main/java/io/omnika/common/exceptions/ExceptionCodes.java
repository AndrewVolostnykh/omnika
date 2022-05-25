package io.omnika.common.exceptions;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ExceptionCodes {

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Validation {
        public static final String EMAIL_REQUIRED = "EMAIL_REQUIRED";
        public static final String PASSWORD_REQUIRED = "PASSWORD_REQUIRED";
        public static final String INVALID_EMAIL_PATTERN = "INVALID_EMAIL_PATTERN";
        public static final String INVALID_PASSWORD_PATTERN = "INVALID_PASSWORD_PATTERN";
        public static final String NOT_UNIQUE = "NOT_UNIQUE";
        public static final String NOT_OWNER_CREATES_MANAGER = "NOT_OWNER_CREATES_MANAGER";
        public static final String OWNER_CANNOT_BE_MANAGER = "OWNER_CANNOT_BE_MANAGER";
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Auth {
        public static final String LOGIN_OR_PASSWORD_INCORRECT = "LOGIN_OR_PASSWORD_INCORRECT";
        public static final String USER_NOT_ACTIVE = "USER_NOT_ACTIVE";
    }

}
