package io.omnika.services.messaging.gateway.service;

import io.omnika.common.rest.services.channels.dto.ChannelMessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageProducerServiceImpl {

    private final MessageProducerProvider messageProducerProvider;

    public ChannelMessageDto send(ChannelMessageDto channelMessageDto) {
        return messageProducerProvider.find(channelMessageDto.getChannelSessionDto().getChannelType())
                .send(channelMessageDto);
    }


}
