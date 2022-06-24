package io.omnika.services.messaging.gateway.service;

import io.omnika.common.ipc.streams.SendToStream;
import io.omnika.common.rest.services.channels.dto.ChannelMessageDto;
import io.omnika.common.rest.services.management.model.ChannelType;
import io.omnika.services.messaging.gateway.converter.ChannelMessageConverter;
import io.omnika.services.messaging.gateway.core.AbstractMessageProducer;
import io.omnika.services.messaging.gateway.repository.ChannelMessageRepository;
import io.omnika.services.messaging.gateway.repository.ChannelSessionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class InstagramMessageProducerService extends AbstractMessageProducer {
    private static final String TO_INSTAGRAM_CHANNEL_MESSAGE_EXCHANGE = "toInstagramChannelMessage";

    public InstagramMessageProducerService(ChannelSessionRepository channelSessionRepository,
            ChannelMessageRepository channelMessageRepository,
            ChannelMessageConverter channelMessageConverter) {
        super(channelSessionRepository, channelMessageRepository, channelMessageConverter);
    }

    // TODO: validate it for can user send messages to this session (channel etc.)
    @Override
    @SendToStream(exchange = TO_INSTAGRAM_CHANNEL_MESSAGE_EXCHANGE)
    public ChannelMessageDto send(ChannelMessageDto channelMessageDto) {
        return super.send(channelMessageDto);
    }

    @Override
    public boolean isApplicable(ChannelType channelType) {
        return ChannelType.INSTAGRAM.equals(channelType);
    }
}
