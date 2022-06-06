package io.omnika.services.management.service;

import io.omnika.common.rest.services.management.dto.channel.ChannelDto;
import io.omnika.common.rest.services.management.model.ChannelType;
import io.omnika.common.utils.hibernate.HibernateUtils;
import io.omnika.services.management.converters.ChannelConverter;
import io.omnika.services.management.core.service.ChannelService;
import io.omnika.services.management.repository.channel.ChannelRepository;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
class ChannelServiceImpl implements ChannelService {

    private final ChannelRepository channelRepository;
    private final ChannelConverter channelConverter;
    private final HibernateUtils hibernateUtils;

    private final StreamBridge streamBridge;

    @Bean
    public Consumer<ChannelType> getAllChannels() {
        // FIXME: do not use hardcoded names of queues, better to use Suplier to publish message to queue
        return channelType -> {
            log.info("Received getAllChannels event with channel type [{}]", channelType);
            streamBridge.send("allChannels-in-0", hibernateUtils.doInNewTransaction(() -> listByType(channelType)));
        };
    }

    // FIXME: change to database view, because it takes too long time to convert every entity to needed type
    // FIXME: add pagination. According to large sets of channels in future, we need to use pagination (yes, also for queue-messaging)
    @Transactional(readOnly = true)
    public List<ChannelDto> listByType(ChannelType channelType) {
        return channelRepository.findAllByChannelType(channelType)
                .stream()
                .map(channelConverter::toDto)
                .collect(Collectors.toList());

    }

    public ChannelDto deactivateChannel(Long channelId) {
        return null;
    }

}
