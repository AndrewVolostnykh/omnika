package io.omnika.services.management.service;

import io.omnika.common.ipc.config.AfterStartUp;
import io.omnika.common.ipc.config.Topics;
import io.omnika.common.ipc.dto.ChannelsRequest;
import io.omnika.common.ipc.events.ChannelEntityEvent;
import io.omnika.common.ipc.events.EntityEventType;
import io.omnika.common.ipc.service.QueueService;
import io.omnika.common.model.channel.Channel;
import io.omnika.common.model.channel.ChannelType;
import io.omnika.common.model.channel.ServiceType;
import io.omnika.services.management.core.service.ChannelService;
import io.omnika.services.management.mappers.ChannelMapper;
import io.omnika.services.management.model.ChannelEntity;
import io.omnika.services.management.repository.channel.ChannelRepository;
import io.omnika.services.management.utils.DaoUtils;
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
    private final ChannelMapper channelMapper;
    private final QueueService queueService;

    @Override
    public Channel createChannel(UUID tenantId, Channel channel) {
        channel.setId(null);
        channel.setTenantId(tenantId);

        ChannelEntity channelEntity = channelMapper.toDomain(channel);
        channelEntity = channelRepository.save(channelEntity);
        // TODO: WARNING! Write a test for converting config json
        channel = channelMapper.toDto(channelEntity);
        sendChannelEvent(channel, EntityEventType.CREATED);
        return channel;
    }

    // FIXME: change to database view, because it takes too long time to convert every entity to needed type
    // FIXME: add pagination. According to large sets of channels in future, we need to use pagination (yes, also for queue-messaging)


    @Override
    public List<Channel> getChannelsByTenantId(UUID tenantId) {
        return channelRepository.findAllByTenantId(tenantId).stream()
                .map(channelMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Page<Channel> getChannelsByType(ChannelType type, PageRequest pageRequest) {
        return channelRepository.findByChannelType(type, pageRequest)
                .map(channelMapper::toDto);
    }

    private void sendChannelEvent(Channel channel, EntityEventType eventType) {
        ChannelEntityEvent channelEntityEvent = new ChannelEntityEvent(channel.getTenantId(), channel.getId(), channel.getConfig(), eventType);
        ServiceType serviceType = channel.getChannelType().getChannelServiceType();
        queueService.sendMessage(serviceType, Topics.channelEvents(channel.getChannelType()), channelEntityEvent, channel.getId());
    }

    @AfterStartUp
    public void initQueueApi() {
        queueService.<ChannelsRequest>subscribeAndRespond(Topics.getChannels(), (channelsRequest, replier) -> {
            DaoUtils.processInBatches(pageRequest -> {
                return getChannelsByType(channelsRequest.getChannelType(), pageRequest);
            }, channel -> {
                ChannelEntityEvent channelEntityEvent = new ChannelEntityEvent(channel.getTenantId(), channel.getId(), channel.getConfig());
                replier.accept(channelEntityEvent);
            }, 100);
        });
    }

}
