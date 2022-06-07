package io.omnika.common.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class FieldValueError extends Error {

    private String field;
    private String value;
}

