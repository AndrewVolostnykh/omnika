package io.omnika.services.management.service;

import io.omnika.common.ipc.streams.SendToStream;
import io.omnika.common.rest.services.management.dto.channel.ChannelDto;
import io.omnika.common.rest.services.management.model.ChannelType;
import io.omnika.common.utils.hibernate.HibernateUtils;
import io.omnika.services.management.converters.ChannelConverter;
import io.omnika.services.management.core.service.ChannelService;
import io.omnika.services.management.model.channel.Channel;
import io.omnika.services.management.repository.channel.ChannelRepository;
import io.omnika.services.management.service.saga.SendAllChannelsSaga;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
class ChannelServiceImpl implements ChannelService {
    private static final String NEW_TELEGRAM_CHANNEL_EXCHANGE = "newTelegramChannel";

    private final ChannelRepository channelRepository;
    private final ChannelConverter channelConverter;
    private final HibernateUtils hibernateUtils;
    private final SendAllChannelsSaga sendAllChannelsSaga;

    @Override
    @SendToStream(exchange = NEW_TELEGRAM_CHANNEL_EXCHANGE)
    public ChannelDto create(UUID tenantId, ChannelDto channelDto) {
        channelDto.setTenantId(tenantId);

        Channel toSave = channelConverter.toDomain(channelDto);

        Channel saved = channelRepository.save(toSave);
        // TODO: WARNING! Write a test for converting config json
        return channelConverter.toDto(saved);
    }

    @Bean
    public Consumer<ChannelType> getAllChannels() {
        return channelType -> {
            log.warn("Received getAllChannels event with channel type [{}]", channelType);
            sendAllChannelsSaga.send(listChannelsToServices(channelType));
        };
    }

    // FIXME: change to database view, because it takes too long time to convert every entity to needed type
    // FIXME: add pagination. According to large sets of channels in future, we need to use pagination (yes, also for queue-messaging)
    private List<ChannelDto> listChannelsToServices(ChannelType channelType) {
        return hibernateUtils.doInNewTransaction(() -> listAllByType(channelType));
    }

//    @Transactional(readOnly = true)
//    public List<ChannelDto> listByType(UUID tenantId, ChannelType channelType) {
//        return channelRepository.findAllByTenantIdAndChannelType(tenantId, channelType).stream()
//                .map(channelConverter::toDto)
//                .collect(Collectors.toList());
//    }

    private List<ChannelDto> listAllByType(ChannelType type) {
        return channelRepository.findAllByChannelType(type).stream()
                .map(channelConverter::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ChannelDto> list(UUID tenantId) {
        return channelRepository.findAllByTenantId(tenantId)
                .stream()
                .map(channelConverter::toDto)
                .collect(Collectors.toList());
    }
}
