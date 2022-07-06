package io.omnika.services.management.service;

import io.omnika.common.model.channel.Channel;
import io.omnika.common.model.channel.ChannelType;
import io.omnika.services.management.converters.ChannelConverter;
import io.omnika.services.management.core.service.ChannelService;
import io.omnika.services.management.model.ChannelEntity;
import io.omnika.services.management.repository.channel.ChannelRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
class ChannelServiceImpl implements ChannelService {
    private final ChannelRepository channelRepository;
    private final ChannelConverter channelConverter;

    @Override
    // how to send event about creating new chat if it can be instagram, telegram, viber?
//    @SendToStream(exchange = NEW_TELEGRAM_CHANNEL_EXCHANGE)
    public Channel create(UUID tenantId, Channel channel) {
        channel.setTenantId(tenantId);

        ChannelEntity toSave = channelConverter.toDomain(channel);

        ChannelEntity saved = channelRepository.save(toSave);
        // TODO: WARNING! Write a test for converting config json
        return channelConverter.toDto(saved);
    }


    // FIXME: change to database view, because it takes too long time to convert every entity to needed type
    // FIXME: add pagination. According to large sets of channels in future, we need to use pagination (yes, also for queue-messaging)


    @Override
    public List<Channel> getChannelsByTenantId(UUID tenantId) {
        return channelRepository.findAllByTenantId(tenantId).stream()
                .map(channelConverter::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Page<Channel> getChannelsByType(ChannelType type, PageRequest pageRequest) {
        return channelRepository.findByChannelType(type, pageRequest)
                .map(channelConverter::toDto);
    }

}
