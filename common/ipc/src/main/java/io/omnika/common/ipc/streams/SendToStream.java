package io.omnika.common.ipc.streams;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface SendToStream {
    String exchange() default "";

//    ExchangeType type() default ExchangeType.IN;
}
