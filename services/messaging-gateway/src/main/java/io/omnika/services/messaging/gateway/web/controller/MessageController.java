package io.omnika.services.messaging.gateway.web.controller;

import io.omnika.common.model.channel.ChannelMessage;
import io.omnika.common.rest.controller.BaseController;
import io.omnika.common.security.core.service.TokenService;
import io.omnika.common.security.model.UserPrincipal;
import io.omnika.services.messaging.gateway.core.service.ChannelMessageService;
import io.omnika.services.messaging.gateway.service.messaging.ChannelMessageProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

import static org.springframework.messaging.simp.SimpMessageHeaderAccessor.USER_HEADER;

@Controller
@RequiredArgsConstructor
public class MessageController extends BaseController {

    private final ChannelMessageProcessor messageProcessor;
    private final ChannelMessageService messageService;
    private final TokenService tokenService;

    // need to subscribe to all session a user has access to
    @MessageMapping("/message/session/{channelSessionId}")
    public void sendMessage(@DestinationVariable UUID channelSessionId,
                            @Payload String text,
                            UserPrincipal userPrincipal) {
        messageProcessor.sendOutboundChannelMessage(userPrincipal.getTenantId(), userPrincipal.getUserId(), channelSessionId, text);
    }

    @MessageMapping("/messages/session/{channelSessionId}")
    public void listMessages() {

    }


    // there web socket connection
    // NOTE: it is important to validate before
    // connection this user is an owner or tenant of
    // current user id an owner of channel to subscribe
    // TODO: websocket connection for consuming messages

    @GetMapping("/list")
    public List<ChannelMessage> listMessages(@RequestParam UUID channelSessionId) {
        return messageService.findChannelMessagesByChannelSessionId(channelSessionId);
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('TENANT_ADMIN', 'MANAGER')")
    public void sendMessage(@RequestParam UUID channelSessionId, @RequestBody String text) {
        messageProcessor.sendOutboundChannelMessage(getTenantId(), getPrincipal().getUserId(), channelSessionId, text);
    }

}
