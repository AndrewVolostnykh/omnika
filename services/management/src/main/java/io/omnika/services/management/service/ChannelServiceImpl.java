package io.omnika.services.management.service;

import io.omnika.common.ipc.streams.SendToStream;
import io.omnika.common.rest.services.management.dto.channel.ChannelDto;
import io.omnika.common.rest.services.management.model.ChannelType;
import io.omnika.common.utils.hibernate.HibernateUtils;
import io.omnika.services.management.converters.ChannelConverter;
import io.omnika.services.management.core.service.ChannelService;
import io.omnika.services.management.model.channel.Channel;
import io.omnika.services.management.repository.channel.ChannelRepository;
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
    private static final String ALL_CHANNELS_EXCHANGE = "allChannels";
    private static final String ALL_TELEGRAM_CHANNELS_EXCHANGE = "allTelegramChannels";
    private static final String ALL_VIBER_CHANNELS_EXCHANGE = "allViberChannels";
    private static final String ALL_INSTAGRAM_CHANNELS_EXCHANGE = "allInstagramChannels";

    private final ChannelRepository channelRepository;
    private final ChannelConverter channelConverter;
    private final HibernateUtils hibernateUtils;

    @Override
    // how to send event about creating new chat if it can be instagram, telegram, viber?
//    @SendToStream(exchange = NEW_TELEGRAM_CHANNEL_EXCHANGE)
    public ChannelDto create(UUID tenantId, ChannelDto channelDto) {
        channelDto.setTenantId(tenantId);

        Channel toSave = channelConverter.toDomain(channelDto);

        Channel saved = channelRepository.save(toSave);
        // TODO: WARNING! Write a test for converting config json
        return channelConverter.toDto(saved);
    }

    @Bean
    public Consumer<ChannelType> getAllChannels(ChannelServiceImpl channelService) {
        return channelType -> {
            log.warn("Received getAllChannels event with channel type [{}]", channelType);
            List<ChannelDto> channelDtos;

            switch (channelType) {
                case TELEGRAM_BOT: {
                    channelDtos = channelService.listTelegramChannelsToServices();
                    break;
                }
                case VIBER_BOT: {
                    channelDtos = channelService.listViberChannelsToServices();
                    break;
                }
                case INSTAGRAM: {
                    channelDtos = channelService.listInstagramChannelsToServices();
                    break;
                }
                default: throw new IllegalStateException(channelType.name());
            }

            log.debug("Sent [{}] channels to services\n [{}]", channelType, channelDtos);
        };
    }

    // FIXME: change to database view, because it takes too long time to convert every entity to needed type
    // FIXME: add pagination. According to large sets of channels in future, we need to use pagination (yes, also for queue-messaging)
//    @SendToStream(exchange = ALL_CHANNELS_EXCHANGE)
//    public List<ChannelDto> listChannelsToServices(ChannelType channelType) {
//        return hibernateUtils.doInNewTransaction(() -> listAllByType(channelType));
//    }

    @SendToStream(exchange = ALL_TELEGRAM_CHANNELS_EXCHANGE)
    public List<ChannelDto> listTelegramChannelsToServices() {
        return hibernateUtils.doInNewTransaction(() -> listAllByType(ChannelType.TELEGRAM_BOT));
    }

    @SendToStream(exchange = ALL_VIBER_CHANNELS_EXCHANGE)
    public List<ChannelDto> listViberChannelsToServices() {
        return hibernateUtils.doInNewTransaction(() -> listAllByType(ChannelType.VIBER_BOT));
    }

    @SendToStream(exchange = ALL_INSTAGRAM_CHANNELS_EXCHANGE)
    public List<ChannelDto> listInstagramChannelsToServices() {
        return hibernateUtils.doInNewTransaction(() -> listAllByType(ChannelType.INSTAGRAM));
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
