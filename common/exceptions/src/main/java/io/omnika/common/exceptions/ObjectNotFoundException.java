package io.omnika.common.exceptions;

import java.util.List;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ObjectNotFoundException extends RuntimeException {

    private final List<FieldValueError> requestParams;

    public ObjectNotFoundException(Object id, Class<?> clazz) {
        requestParams = List.of(
                FieldValueError.builder()
                        .field(id.toString())
                        .value(clazz.toString())
                        .code(ExceptionCodes.NotFound.OBJECT_NOT_FOUND)
                        .build());
    }

    public ObjectNotFoundException(Object id, Class<?> clazz, String code) {
        requestParams = List.of(
                FieldValueError.builder()
                        .field(id.toString())
                        .value(clazz.toString())
                        .code(code)
                        .build());
    }

}
