package io.omnika.services.messaging.gateway.service;

import io.omnika.common.model.channel.ChannelType;
import io.omnika.common.model.channel.Sender;
import io.omnika.services.messaging.gateway.core.service.SenderService;
import io.omnika.services.messaging.gateway.mappers.SenderMapper;
import io.omnika.services.messaging.gateway.model.SenderEntity;
import io.omnika.services.messaging.gateway.repository.SenderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
class SenderServiceImpl implements SenderService {

    private final SenderRepository senderRepository;
    private final SenderMapper senderMapper;

    @Override
    public Sender createSender(Sender sender) {
        SenderEntity senderEntity = senderMapper.toDomain(sender);
        senderEntity = senderRepository.save(senderEntity);
        return senderMapper.toDto(senderEntity);
    }

    @Override
    public Sender findSenderByExternalIdAndChannelType(String externalId, ChannelType channelType) {
        return Optional.ofNullable(senderRepository.findByExternalIdAndChannelType(externalId, channelType))
                .map(senderMapper::toDto)
                .orElse(null);
    }

    @Override
    public Sender findSenderById(UUID id) {
        return senderRepository.findById(id)
                .map(senderMapper::toDto)
                .orElse(null);
    }

}
