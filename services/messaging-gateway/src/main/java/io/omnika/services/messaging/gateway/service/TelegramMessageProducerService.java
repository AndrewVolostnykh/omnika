package io.omnika.services.messaging.gateway.service;

import io.omnika.common.rest.services.channels.dto.ChannelMessageDto;
import io.omnika.common.rest.services.management.model.ChannelType;
import io.omnika.services.messaging.gateway.converter.ChannelMessageConverter;
import io.omnika.services.messaging.gateway.core.MessageProducer;
import io.omnika.services.messaging.gateway.model.ChannelMessage;
import io.omnika.services.messaging.gateway.repository.ChannelMessageRepository;
import io.omnika.services.messaging.gateway.repository.SessionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TelegramMessageProducerService implements MessageProducer {

    private final StreamBridge streamBridge;
    private final SessionRepository sessionRepository;
    private final ChannelMessageRepository channelMessageRepository;
    private final ChannelMessageConverter channelMessageConverter;

    // TODO: validate it for can user send messages to this session (channel etc.)
    @Override
    public ChannelMessageDto send(ChannelMessageDto channelMessageDto) {
        ChannelMessage channelMessage = channelMessageConverter.toDomain(channelMessageDto);

        // TODO: cache
        if (sessionRepository.existsBySessionIdAndChannelId(channelMessageDto.getChannelSessionDto().getSessionId(), channelMessageDto.getChannelSessionDto().getChannelId())) {
            channelMessage.setChannelSession(sessionRepository.getById(channelMessageDto.getChannelSessionDto().getId()));
        } else {
            sessionRepository.save(channelMessage.getChannelSession());
        }

        ChannelMessage saved = channelMessageRepository.save(channelMessage);

        log.warn("Sending message to channel [{}] with session [{}] with text [{}]",
                channelMessage.getChannelSession().getChannelId(),
                channelMessage.getChannelSession().getSessionId(),
                channelMessage.getText());

        streamBridge.send("toTelegramChannelMessage-in-0", channelMessageDto);

        return channelMessageConverter.toDto(saved);
    }

    @Override
    public boolean isApplicable(ChannelType channelType) {
        return ChannelType.TELEGRAM_BOT.equals(channelType);
    }
}
