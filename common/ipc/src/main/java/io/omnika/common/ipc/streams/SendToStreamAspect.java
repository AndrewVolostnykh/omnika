package io.omnika.common.ipc.streams;

import java.lang.reflect.Method;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class SendToStreamAspect {

    private final StreamBridge streamBridge;
    private static final String EXCHANGE_FORMULA = "%s-in-0";

    @Around("@annotation(io.omnika.common.ipc.streams.SendToStream)")
    public Object sendToStreamAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object toSend = proceedingJoinPoint.proceed();

        MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
        Method method = methodSignature.getMethod();
        SendToStream sendToStreamAnnotation = method.getAnnotation(SendToStream.class);

        String exchange = sendToStreamAnnotation.exchange();

        if (exchange.isBlank()) {
            exchange = method.getName();
        }

        streamBridge.send(String.format(EXCHANGE_FORMULA, exchange), toSend);

        return toSend;
    }
}
