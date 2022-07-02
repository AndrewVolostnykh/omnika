package io.omnika.common.exceptions;

public class BasicException extends RuntimeException {

    private final String code;

    public BasicException(String code) {
        this.code = code;
    }

    protected String getCode() {
        return this.code;
    }
}
