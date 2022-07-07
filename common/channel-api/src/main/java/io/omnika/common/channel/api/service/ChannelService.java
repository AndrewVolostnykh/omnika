package io.omnika.common.channel.api.service;

import io.omnika.common.ipc.config.Topics;
import io.omnika.common.ipc.service.QueueService;
import io.omnika.common.ipc.service.ServiceType;
import io.omnika.common.model.channel.ChannelConfig;
import io.omnika.common.model.channel.ChannelMessage;
import io.omnika.common.model.channel.ChannelType;
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

    protected abstract void sendMessage(String sessionId, String text) throws Exception; // todo: take common interface as a message request

    public final void onNewMessage(String sessionId, String text) {
        ChannelMessage channelMessage = ChannelMessage.builder()
                .sessionId(sessionId)
                .text(text)
                .build();
        queueService.sendMessage(ServiceType.MESSAGING_GATEWAY, Topics.newChannelMessages(), channelMessage, channelId);
    }

    public void stop() throws Exception {}

    public abstract ChannelType getType();

}
