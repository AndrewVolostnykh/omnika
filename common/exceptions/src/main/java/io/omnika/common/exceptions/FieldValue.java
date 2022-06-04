package io.omnika.common.exceptions;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FieldValue {

    private String field;
    private String value;
}
