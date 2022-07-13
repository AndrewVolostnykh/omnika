package io.omnika.services.messaging.gateway.web.ws.config;

import io.omnika.common.security.core.service.TokenService;
import io.omnika.common.security.model.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodParameter;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.converter.StringMessageConverter;
import org.springframework.messaging.handler.invocation.HandlerMethodArgumentResolver;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@Configuration
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    public static void main(String[] args) {
        WebSocketClient webSocketClient = new StandardWebSocketClient();
        WebSocketStompClient stompClient = new WebSocketStompClient(webSocketClient);
        stompClient.setMessageConverter(new StringMessageConverter());

        StompSessionHandler sessionHandler = new MyStompSessionHandler();
        WebSocketHttpHeaders headers = new WebSocketHttpHeaders();
        headers.set("Authorization", "eyJhbGciOiJIUzUxMiJ9.eyJ1c2VyX2lkIjoiN2YzMmMxNTItOWE4ZC00YzFiLTk4MzYtMjNhNTg5NTE4ZTE1IiwiZW1haWwiOiJhYmFAZ21haWwuY29tIiwiYXV0aG9yaXR5IjoiVEVOQU5UX0FETUlOIiwidGVuYW50X2lkIjoiMjczZjEzYTYtMjMyNC00MDI3LTkzMzgtNzVjYWEzNmE5ZGFhIiwiaXNzdWVkX2F0IjoxNjU3NjM4MDY0NTAxLCJleHBpcmF0aW9uX2RhdGUiOjE2NTc2NDUyNjQ1MDF9.OxCpizoKeAeQY4i7MoZOhWDvXb5C5fS_bUR2TQ3Ua1XD4dSHdFc-vFXWPVnSQAaXi7aBXQ4Es4KcjASgt-NrBw");
        stompClient.connect("ws://localhost:8083/test", headers, sessionHandler);

        new Scanner(System.in).nextLine();
    }

    public static class MyStompSessionHandler extends StompSessionHandlerAdapter {

        @Override
        public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
            // /7f32c152-9a8d-4c1b-9836-23a589518e15/messages/session/88012616-5dd4-4729-b531-9ec0f83d474c
//            7f32c152-9a8d-4c1b-9836-23a589518e15
            session.subscribe("/secured/user/messages/session/88012616-5dd4-4729-b531-9ec0f83d474c", this);
        }

        @Override
        public Type getPayloadType(StompHeaders headers) {
            return String.class;
        }


        @Override
        public void handleFrame(StompHeaders headers, Object payload) {
            System.err.println("Received: " + payload);
        }

        @Override
        public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload, Throwable exception) {
            exception.printStackTrace();
        }
    }


//
//    @Value("${spring.rabbitmq.host}")
//    private String rabbitMqHost;
//    @Value("${spring.rabbitmq.port}")
//    private int rabbitMqPort;
//    @Value("${spring.rabbitmq.username}")
//    private String rabbitMqUsername;
//    @Value("${spring.rabbitmq.password}")
//    private String rabbitMqPassword;

    private final WebSocketHandshakeHandler handshakeHandler;
    private final TokenService tokenService;
    @Value("${security.token.request_header:Authorization}")
    private String tokenRequestHeader;

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws")
                .setHandshakeHandler(handshakeHandler)
                .withSockJS();
        registry.addEndpoint("/test")
                .setHandshakeHandler(handshakeHandler)
                .setAllowedOrigins("*");
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(new ChannelInterceptor() {
            @Override
            public Message<?> preSend(Message<?> message, MessageChannel channel) {
                UserPrincipal userPrincipal = null;
                try {
                    userPrincipal = retrievePrincipal(message);
                } catch (Exception ignored) {
                }

                StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(message);
                String destination = StringUtils.trim(headerAccessor.getDestination());
                if (StringUtils.startsWith(destination, "/user")) {
                    if (userPrincipal == null || !userPrincipal.getUserId().toString().equals(StringUtils.substringBetween(destination, "/user/", "/"))) {
                        return null;
                    }
                }
                return message;
            }
        });
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new HandlerMethodArgumentResolver() {
            @Override
            public boolean supportsParameter(MethodParameter parameter) {
                return parameter.getParameterType().equals(UserPrincipal.class);
            }

            @Override
            public Object resolveArgument(MethodParameter parameter, Message<?> message) throws Exception {
                return retrievePrincipal(message);
            }
        });
    }

    private UserPrincipal retrievePrincipal(Message<?> message) throws IllegalArgumentException {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(message);
        UserPrincipal userPrincipal;
        try {
            String token = Optional.ofNullable(headerAccessor.getNativeHeader(tokenRequestHeader.toLowerCase()))
                    .flatMap(values -> values.stream().findFirst())
                    .orElse(null);
            userPrincipal = tokenService.parseToken(token);
        } catch (Exception e) {
            throw new IllegalArgumentException("No principal");
        }
        return userPrincipal;
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.setApplicationDestinationPrefixes("/api");
        registry.setUserDestinationPrefix("/secured/user");
//        registry.enableSimpleBroker("/topic");
//        registry.enableStompBrokerRelay("/topic")
//                .setRelayHost(rabbitMqHost)
//                .setRelayPort(rabbitMqPort)
//                .setClientLogin(rabbitMqUsername)
//                .setClientPasscode(rabbitMqPassword);
    }

}
