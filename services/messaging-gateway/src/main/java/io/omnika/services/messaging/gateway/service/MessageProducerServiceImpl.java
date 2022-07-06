package io.omnika.services.messaging.gateway.service;

import io.omnika.common.model.channel.ChannelMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageProducerServiceImpl {

    private final MessageProducerProvider messageProducerProvider;

    public ChannelMessage send(ChannelMessage channelMessage) {
//        return messageProducerProvider.find(channelMessage.getChannelSession().getChannelType())
//                .send(channelMessage);
        return null;
    }

}
