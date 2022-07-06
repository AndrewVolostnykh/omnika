package io.omnika.services.messaging.gateway.service;

import io.omnika.common.exceptions.ObjectNotFoundException;
import io.omnika.common.model.channel.ChannelMessage;
import io.omnika.services.messaging.gateway.converter.ChannelMessageConverter;
import io.omnika.services.messaging.gateway.model.ChannelMessageEntity;
import io.omnika.services.messaging.gateway.model.ChannelSessionEntity;
import io.omnika.services.messaging.gateway.repository.ChannelMessageRepository;
import io.omnika.services.messaging.gateway.repository.ChannelSessionRepository;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
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
    public Consumer<ChannelMessage> message() {
        return message -> {
            // We have to ignore every of messages already present in database.
            // An artifacts when to gateway incoming present messages can produce instagram channel service
            // TODO: Cache
//            log.warn("[RECEIVED] Received but not processed [{}], channel id", message.getText(), message.getChannelSession().getChannelId());

            if (StringUtils.isNotBlank(message.getInternalId()) && channelMessageRepository.existsByInternalId(message.getInternalId())) {
                return;
            }
//
//            log.warn("[NEW MESSAGE] Received new message with text [{}] channel id [{}] session id [{}]",
//                    message.getText(),
//                    message.getChannelSession().getChannelId(),
//                    message.getChannelSession().getSessionId());

            ChannelMessageEntity channelMessage = channelMessageConverter.toDomain(message);

            ChannelSessionEntity channelSession;
            // there should be used some cache, because on every message not so
            // efficient to go to DB and check for a session presents
            // FIXME: exists by id and channel id
//            if (channelSessionRepository
//                    .existsBySessionIdAndChannelId(message.getChannelSession().getSessionId(), message.getChannelSession().getChannelId())) {
//                channelSession = channelSessionRepository
//                        .findBySessionIdAndChannelId(message.getChannelSession().getSessionId(), message.getChannelSession().getChannelId())
//                        .orElseThrow(() -> new ObjectNotFoundException(message.getChannelSession().getSessionId(), ChannelSessionEntity.class));
//
//                channelMessage.setChannelSession(channelSession);
//            } else {
//                channelSessionRepository.save(channelMessage.getChannelSession());
//            }

            channelMessageRepository.save(channelMessage);
        };
    }

    @Transactional(readOnly = true)
    public List<ChannelMessage> allMessages() {
        return channelMessageRepository.findAll()
                .stream()
                .map(channelMessageConverter::toDto)
                .collect(Collectors.toList());
    }
}
