package io.omnika.common.exceptions;

import java.util.List;

// TODO: cover it with exception handler in BaseController
public class ObjectNotFoundException extends RuntimeException {

    private final List<FieldValue> requestParams;

    public ObjectNotFoundException(Object id, Class<?> clazz) {
        requestParams = List.of(FieldValue.builder().field(id.toString()).value(clazz.toString()).build());
    }

}
