package io.omnika.services.management.core.service;

import io.omnika.common.model.channel.Channel;
import io.omnika.common.model.channel.ChannelType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.UUID;

public interface ChannelService {

    Channel create(UUID tenantId, Channel channel);

    List<Channel> getChannelsByTenantId(UUID tenantId);

    Page<Channel> getChannelsByType(ChannelType channelType, PageRequest pageRequest);

}
