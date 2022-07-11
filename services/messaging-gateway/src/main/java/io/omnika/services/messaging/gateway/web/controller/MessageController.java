package io.omnika.services.messaging.gateway.web.controller;

import io.omnika.common.model.channel.ChannelMessage;
import io.omnika.common.rest.controller.BaseController;
import io.omnika.services.messaging.gateway.core.service.ChannelMessageService;
import io.omnika.services.messaging.gateway.service.ChannelMessageProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

// TODO: user basic controller and interface from rest
@RestController
@RequestMapping("/message")
@RequiredArgsConstructor
public class MessageController extends BaseController {

    private final ChannelMessageProcessor messageProcessor;
    private final ChannelMessageService messageService;

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
