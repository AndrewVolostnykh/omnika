package io.omnika.services.messaging.gateway.service;

import io.omnika.common.model.channel.ChannelMessage;
import io.omnika.common.model.channel.ChannelType;
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
//    @SendToStream(binding = TO_INSTAGRAM_CHANNEL_MESSAGE_EXCHANGE)
    public ChannelMessage send(ChannelMessage channelMessage) {
        return super.send(channelMessage);
    }

    @Override
    public boolean isApplicable(ChannelType channelType) {
        return ChannelType.INSTAGRAM.equals(channelType);
    }
}
