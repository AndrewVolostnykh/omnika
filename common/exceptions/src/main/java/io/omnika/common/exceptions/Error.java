package io.omnika.common.exceptions;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class Error {

    private String code;

}
