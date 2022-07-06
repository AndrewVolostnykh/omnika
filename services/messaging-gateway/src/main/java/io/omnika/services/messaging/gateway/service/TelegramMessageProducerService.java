package io.omnika.services.messaging.gateway.service;

import io.omnika.common.exceptions.ValidationException;
import io.omnika.common.model.channel.ChannelMessage;
import io.omnika.common.model.channel.ChannelType;
import io.omnika.common.security.utils.AuthenticationHelper;
import io.omnika.services.messaging.gateway.converter.ChannelMessageConverter;
//import io.omnika.services.messaging.gateway.core.MessageProducer;
import io.omnika.services.messaging.gateway.core.MessageProducer;
import io.omnika.services.messaging.gateway.model.ChannelMessageEntity;
import io.omnika.services.messaging.gateway.model.ChannelSessionEntity;
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
//    @SendToStream(TO_TELEGRAM_CHANNEL_MESSAGE_EXCHANGE)
    public ChannelMessage send(ChannelMessage channelMessageDto) {
        ChannelMessageEntity channelMessage = channelMessageConverter.toDomain(channelMessageDto);
        ChannelSessionEntity channelSession;

        // TODO: cache
//        if (channelSessionRepository.existsBySessionIdAndChannelId(channelMessageDto.getChannelSession().getSessionId(), channelMessageDto.getChannelSession().getChannelId())) {
//            channelSession = channelSessionRepository.getById(channelMessageDto.getChannelSession().getId());
//            channelMessage.setChannelSession(channelSession);
//
//            if (!AuthenticationHelper.getAuthenticationDetails().getTenantId().equals(channelSession.getTenantId())) {
//                throw new ValidationException("U cannot write to not ur tenant or own chat");
//            }
//
//        } else {
//            // TODO: make request to management service to check can user write for the channel
//            // just send tenant id and channel id and receive boolean is it allowed or not
//            channelSessionRepository.save(channelMessage.getChannelSession());
//        }

        ChannelMessageEntity saved = channelMessageRepository.save(channelMessage);

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
