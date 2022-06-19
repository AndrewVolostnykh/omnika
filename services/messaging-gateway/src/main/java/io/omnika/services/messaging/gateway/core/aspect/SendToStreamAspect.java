package io.omnika.services.messaging.gateway.core.aspect;

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

    @Around("@annotation(SendToStream)")
    public Object sendToStreamAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object toSend = proceedingJoinPoint.proceed();

        SendToStream sendToStreamAnnotation = getSendToStreamAnnotation(proceedingJoinPoint);

        streamBridge.send(sendToStreamAnnotation.exchange(), toSend);

        return toSend;
    }

    public SendToStream getSendToStreamAnnotation(ProceedingJoinPoint proceedingJoinPoint) {
        MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
        Method method = methodSignature.getMethod();
        return method.getAnnotation(SendToStream.class);
    }
}
