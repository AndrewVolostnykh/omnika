package io.omnika.services.management.queue;

import io.omnika.common.ipc.config.Topics;
import io.omnika.common.ipc.dto.ChannelResponse;
import io.omnika.common.ipc.dto.ChannelsRequest;
import io.omnika.common.ipc.service.QueueService;
import io.omnika.services.management.core.service.ChannelService;
import io.omnika.services.management.utils.DaoUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
public class QueueRequestHandler {

    private final QueueService queueService;
    private final ChannelService channelService;

    @PostConstruct
    private void initApi() {
        queueService.<ChannelsRequest>subscribeAndRespond(Topics.getChannels(), (channelsRequest, replier) -> {
            DaoUtils.processInBatches(pageRequest -> {
                return channelService.getChannelsByType(channelsRequest.getChannelType(), pageRequest);
            }, channel -> {
                ChannelResponse channelResponse = ChannelResponse.builder()
                        .channelId(channel.getId())
                        .tenantId(channel.getTenantId())
                        .channelConfig(channel.getConfig())
                        .build();
                replier.accept(channelResponse);
            }, 100);
        });
    }

}
