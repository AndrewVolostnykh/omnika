package io.omnika.services.messaging.gateway.service;

import io.omnika.common.model.channel.ChannelMessage;
import io.omnika.services.messaging.gateway.core.service.ChannelMessageService;
import io.omnika.services.messaging.gateway.mappers.ChannelMessageMapper;
import io.omnika.services.messaging.gateway.model.ChannelMessageEntity;
import io.omnika.services.messaging.gateway.repository.ChannelMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
class ChannelMessageServiceImpl implements ChannelMessageService {

    private final ChannelMessageRepository channelMessageRepository;
    private final ChannelMessageMapper channelMessageMapper;

    @Transactional
    @Override
    public ChannelMessage createChannelMessage(ChannelMessage channelMessage) {
        ChannelMessageEntity channelMessageEntity = channelMessageMapper.toDomain(channelMessage);
        channelMessageEntity = channelMessageRepository.save(channelMessageEntity);
        return channelMessageMapper.toDto(channelMessageEntity);
    }

    @Transactional(readOnly = true)
    @Override
    public List<ChannelMessage> findChannelMessagesByChannelSessionId(UUID channelSessionId) {
        return channelMessageRepository.findByChannelSessionId(channelSessionId).stream()
                .map(channelMessageMapper::toDto)
                .collect(Collectors.toList());
    }

}
