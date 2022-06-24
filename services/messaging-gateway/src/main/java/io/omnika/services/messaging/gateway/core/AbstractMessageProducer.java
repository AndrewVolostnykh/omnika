package io.omnika.services.messaging.gateway.core;

import io.omnika.common.exceptions.ValidationException;
import io.omnika.common.rest.services.channels.dto.ChannelMessageDto;
import io.omnika.common.security.utils.AuthenticationHelper;
import io.omnika.services.messaging.gateway.converter.ChannelMessageConverter;
import io.omnika.services.messaging.gateway.model.ChannelMessage;
import io.omnika.services.messaging.gateway.model.ChannelSession;
import io.omnika.services.messaging.gateway.repository.ChannelMessageRepository;
import io.omnika.services.messaging.gateway.repository.ChannelSessionRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractMessageProducer implements MessageProducer {

    private final ChannelSessionRepository channelSessionRepository;
    private final ChannelMessageRepository channelMessageRepository;
    private final ChannelMessageConverter channelMessageConverter;

    protected AbstractMessageProducer(ChannelSessionRepository channelSessionRepository,
            ChannelMessageRepository channelMessageRepository,
            ChannelMessageConverter channelMessageConverter) {
        this.channelSessionRepository = channelSessionRepository;
        this.channelMessageRepository = channelMessageRepository;
        this.channelMessageConverter = channelMessageConverter;
    }

    @Override
    public ChannelMessageDto send(ChannelMessageDto channelMessageDto) {
        ChannelMessage channelMessage = channelMessageConverter.toDomain(channelMessageDto);
        ChannelSession channelSession;

        // TODO: cache
        if (channelSessionRepository.existsBySessionIdAndChannelId(channelMessageDto.getChannelSessionDto().getSessionId(), channelMessageDto.getChannelSessionDto().getChannelId())) {
            channelSession = channelSessionRepository.getById(channelMessageDto.getChannelSessionDto().getId());
            channelMessage.setChannelSession(channelSession);

            if (!AuthenticationHelper.getAuthenticationDetails().getTenantId().equals(channelSession.getTenantId())) {
                throw new ValidationException("U cannot write to not ur tenant or own chat");
            }

        } else {
            // TODO: make request to management service to check can user write for the channel
            // just send tenant id and channel id and receive boolean is it allowed or not
            channelSessionRepository.save(channelMessage.getChannelSession());
        }

        ChannelMessage saved = channelMessageRepository.save(channelMessage);

        log.warn("Sending message to channel [{}] with session [{}] with text [{}]",
                channelMessage.getChannelSession().getChannelId(),
                channelMessage.getChannelSession().getSessionId(),
                channelMessage.getText());

        return channelMessageConverter.toDto(saved);
    }
}
