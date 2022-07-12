package io.omnika.services.messaging.gateway.web.ws.config;

import io.omnika.common.security.core.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

@Component
@RequiredArgsConstructor
public class WebSocketHandshakeHandler extends DefaultHandshakeHandler {

    private final TokenService tokenService;

}
