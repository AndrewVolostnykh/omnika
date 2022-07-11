package io.omnika.common.channel.api.service;

import io.omnika.common.ipc.config.Topics;
import io.omnika.common.ipc.dto.InboundChannelMessage;
import io.omnika.common.ipc.dto.OutboundChannelMessage;
import io.omnika.common.ipc.service.QueueService;
import io.omnika.common.model.channel.ChannelConfig;
import io.omnika.common.model.channel.ChannelType;
import io.omnika.common.model.channel.ServiceType;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

public abstract class ChannelService<C extends ChannelConfig> {

    private final UUID tenantId;
    private final UUID channelId;
    @Getter
    protected final C config;

    @Autowired
    private QueueService queueService;

    public ChannelService(UUID tenantId, UUID channelId, C config) throws Exception {
        this.tenantId = tenantId;
        this.channelId = channelId;
        this.config = config;
    }

    public abstract void init() throws Exception;

    protected abstract void sendMessage(OutboundChannelMessage message) throws Exception;

    public final void onNewMessage(InboundChannelMessage message) {
        message.setChannelType(getType());
        message.setChannelId(channelId);
        message.setTenantId(tenantId);
        queueService.sendMessage(ServiceType.MESSAGING_GATEWAY, Topics.inboundChannelMessages(), message, channelId);
    }

    public void stop() throws Exception {}

    public abstract ChannelType getType();

}
