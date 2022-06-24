package io.omnika.services.messaging.gateway.service;

import io.omnika.common.exceptions.ValidationException;
import io.omnika.common.ipc.streams.SendToStream;
import io.omnika.common.rest.services.channels.dto.ChannelMessageDto;
import io.omnika.common.rest.services.management.model.ChannelType;
import io.omnika.common.security.utils.AuthenticationHelper;
import io.omnika.services.messaging.gateway.converter.ChannelMessageConverter;
import io.omnika.services.messaging.gateway.core.MessageProducer;
import io.omnika.services.messaging.gateway.model.ChannelMessage;
import io.omnika.services.messaging.gateway.model.ChannelSession;
import io.omnika.services.messaging.gateway.repository.ChannelMessageRepository;
import io.omnika.services.messaging.gateway.repository.ChannelSessionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TelegramMessageProducerService implements MessageProducer {
    private static final String TO_TELEGRAM_CHANNEL_MESSAGE_EXCHANGE = "toTelegramChannelMessage";

    private final ChannelSessionRepository channelSessionRepository;
    private final ChannelMessageRepository channelMessageRepository;
    private final ChannelMessageConverter channelMessageConverter;

    // TODO: validate it for can user send messages to this session (channel etc.)
    @Override
    @SendToStream(exchange = TO_TELEGRAM_CHANNEL_MESSAGE_EXCHANGE)
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

    @Override
    public boolean isApplicable(ChannelType channelType) {
        return ChannelType.TELEGRAM_BOT.equals(channelType);
    }
}
