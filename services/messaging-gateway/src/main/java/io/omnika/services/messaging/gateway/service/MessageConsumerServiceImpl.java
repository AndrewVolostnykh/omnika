package io.omnika.services.messaging.gateway.service;

import io.omnika.common.exceptions.ObjectNotFoundException;
import io.omnika.common.rest.services.channels.dto.ChannelMessageDto;
import io.omnika.services.messaging.gateway.converter.ChannelMessageConverter;
import io.omnika.services.messaging.gateway.model.ChannelMessage;
import io.omnika.services.messaging.gateway.model.ChannelSession;
import io.omnika.services.messaging.gateway.repository.ChannelMessageRepository;
import io.omnika.services.messaging.gateway.repository.ChannelSessionRepository;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageConsumerServiceImpl {

    private final ChannelSessionRepository channelSessionRepository;
    private final ChannelMessageRepository channelMessageRepository;
    private final ChannelMessageConverter channelMessageConverter;

    // WARNING! If we will throw message in time of messaging gateway starting up
    // it will not process this message, only new received after start up
    @Bean
    public Consumer<ChannelMessageDto> message() {
        return message -> {
            log.warn("[NEW MESSAGE] Received new message with text [{}] channel id [{}] session id [{}]",
                    message.getText(),
                    message.getChannelSessionDto().getChannelId(),
                    message.getChannelSessionDto().getSessionId());

            ChannelMessage channelMessage = channelMessageConverter.toDomain(message);

            ChannelSession channelSession;
            // there should be used some cache, because on every message not so
            // efficient to go to DB and check for a session presents
            // FIXME: exists by id and channel id
            if (channelSessionRepository
                    .existsBySessionIdAndChannelId(message.getChannelSessionDto().getSessionId(), message.getChannelSessionDto().getChannelId())) {
                channelSession = channelSessionRepository
                        .findBySessionIdAndChannelId(message.getChannelSessionDto().getSessionId(), message.getChannelSessionDto().getChannelId())
                        .orElseThrow(() -> new ObjectNotFoundException(message.getChannelSessionDto().getSessionId(), ChannelSession.class));

                channelMessage.setChannelSession(channelSession);
            } else {
                channelSessionRepository.save(channelMessage.getChannelSession());
            }

            channelMessageRepository.save(channelMessage);
        };
    }

    @Transactional(readOnly = true)
    public List<ChannelMessageDto> allMessages() {
        return channelMessageRepository.findAll()
                .stream()
                .map(channelMessageConverter::toDto)
                .collect(Collectors.toList());
    }

}
