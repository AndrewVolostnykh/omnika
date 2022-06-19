package io.omnika.services.messaging.gateway.web.controller;

import io.omnika.common.rest.services.channels.dto.ChannelMessageDto;
import io.omnika.services.messaging.gateway.service.MessageConsumerServiceImpl;
import io.omnika.services.messaging.gateway.service.MessageProducerServiceImpl;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// TODO: user basic controller and interface from rest
@RestController
@RequestMapping("/messages")
@RequiredArgsConstructor
public class MessageConsumerController {

    private final MessageConsumerServiceImpl messageConsumerService;
    private final MessageProducerServiceImpl messageProducerService;

    // there web socket connection
    // NOTE: it is important to validate before
    // connection this user is an owner or tenant of
    // current user id an owner of channel to subscribe
    // TODO: websocket connection for consuming messages

    @GetMapping("/list")
    public List<ChannelMessageDto> listAllMessages() {
        return messageConsumerService.allMessages();
    }

    @PostMapping
    public ChannelMessageDto sendMessage(@RequestBody ChannelMessageDto channelMessageDto) {
        return messageProducerService.send(channelMessageDto);
    }
}
