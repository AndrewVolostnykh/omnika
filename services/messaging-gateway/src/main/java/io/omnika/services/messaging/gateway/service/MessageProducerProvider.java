package io.omnika.services.messaging.gateway.service;

import io.omnika.common.exceptions.GenericException;
import io.omnika.common.exceptions.WithDescriptionError;
import io.omnika.common.rest.services.management.model.ChannelType;
import io.omnika.services.messaging.gateway.core.MessageProducer;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MessageProducerProvider {

    private final List<MessageProducer> messageProducers;

    public MessageProducer find(ChannelType channelType) {
        return messageProducers.stream()
                .filter(messageProducer -> messageProducer.isApplicable(channelType))
                .findFirst()
                .orElseThrow(() -> new GenericException(WithDescriptionError.builder()
                        .description("Cannot produce message for channel type: " + channelType)
                        .build()));
    }

}
