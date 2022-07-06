package io.omnika.services.messaging.gateway.web.controller;

import io.omnika.common.rest.controller.BaseController;
import io.omnika.common.model.channel.ChannelMessage;
import io.omnika.services.messaging.gateway.service.MessageConsumerServiceImpl;
import io.omnika.services.messaging.gateway.service.MessageProducerServiceImpl;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// TODO: user basic controller and interface from rest
@RestController
@RequestMapping("/messages")
@RequiredArgsConstructor
public class MessageConsumerController extends BaseController {

    private final MessageConsumerServiceImpl messageConsumerService;
    private final MessageProducerServiceImpl messageProducerService;

    // there web socket connection
    // NOTE: it is important to validate before
    // connection this user is an owner or tenant of
    // current user id an owner of channel to subscribe
    // TODO: websocket connection for consuming messages

    @GetMapping("/list")
    // TODO: remove
    public List<ChannelMessage> listAllMessages() {
        return messageConsumerService.allMessages();
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('TENANT_ADMIN', 'MANAGER')")
    public ChannelMessage sendMessage(@RequestBody ChannelMessage channelMessage) {
        return messageProducerService.send(channelMessage);
    }
}
