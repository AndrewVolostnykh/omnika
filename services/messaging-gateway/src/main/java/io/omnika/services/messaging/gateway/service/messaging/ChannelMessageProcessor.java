package io.omnika.services.messaging.gateway.service.messaging;

import io.omnika.common.ipc.config.Topics;
import io.omnika.common.ipc.dto.InboundChannelMessage;
import io.omnika.common.ipc.dto.OutboundChannelMessage;
import io.omnika.common.ipc.service.QueueService;
import io.omnika.common.model.channel.ChannelMessage;
import io.omnika.common.model.channel.ChannelMessageType;
import io.omnika.common.model.channel.ChannelSession;
import io.omnika.common.model.channel.ChannelType;
import io.omnika.common.model.channel.Sender;
import io.omnika.services.messaging.gateway.core.service.ChannelMessageService;
import io.omnika.services.messaging.gateway.core.service.ChannelSessionService;
import io.omnika.services.messaging.gateway.core.service.SenderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChannelMessageProcessor {

    private final QueueService queueService;
    private final ChannelSessionService channelSessionService;
    private final SenderService senderService;
    private final ChannelMessageService channelMessageService;
    private final ChannelMessageSubscriptionService channelMessageSubscriptionService;

    @PostConstruct
    private void init() {
        queueService.subscribe(Topics.inboundChannelMessages(), this::handleInboundChannelMessage);
    }

    private void handleInboundChannelMessage(InboundChannelMessage message) {
        ChannelSession channelSession = channelSessionService.findChannelSessionByExternalIdAndChannelId(message.getSessionId(), message.getChannelId());
        /*
        * store in cache the list of channels allowed for the user
        * when channel configuration changes -> update this list (e.g. if assigned users list was updated)
        * if channel is now not accessible - send ws event about removal of the sessions for this channel
        * */
        if (channelSession == null) {
            channelSession = new ChannelSession();
            channelSession.setName(message.getUserName());
            channelSession.setExternalId(message.getSessionId());
            channelSession.setChannelId(message.getChannelId());
            channelSession.setTenantId(message.getTenantId());
            channelSession.setChannelType(message.getChannelType());
            channelSession = channelSessionService.createChannelSession(message.getTenantId(), channelSession);
        }
        log.info("New inbound channel message for {} session: {}", message.getChannelType(), message);

        Sender sender = senderService.findSenderByExternalIdAndChannelType(message.getUserId(), message.getChannelType());
        if (sender == null) {
            sender = new Sender();
            sender.setExternalId(message.getUserId());
            sender.setChannelType(message.getChannelType());
            sender = senderService.createSender(sender);
        }

        ChannelMessage channelMessage = new ChannelMessage();
        channelMessage.setChannelSessionId(channelSession.getId());
        channelMessage.setSenderId(sender.getId());
        channelMessage.setText(message.getText());
        channelMessage.setMessageType(ChannelMessageType.INBOUND);
        channelMessage.setReceivedAt(LocalDateTime.now());
        channelMessage.setExternalId(message.getId());
        channelMessage = channelMessageService.createChannelMessage(channelMessage);

        channelMessageSubscriptionService.handleNewMessage(channelSession, channelMessage);
    }

    public void sendOutboundChannelMessage(UUID tenantId, UUID userId, UUID channelSessionId, String text) {
        ChannelSession channelSession = channelSessionService.findChannelSessionByTenantIdAndId(tenantId, channelSessionId);
        if (channelSession == null) {
            throw new IllegalArgumentException("Unknown channel session");
        }
        Sender sender = senderService.findSenderByExternalId(userId.toString());
        if (sender == null) {
            sender = new Sender();
            sender.setExternalId(userId.toString());
            sender = senderService.createSender(sender);
        }

        OutboundChannelMessage message = OutboundChannelMessage.builder()
                .channelId(channelSession.getChannelId())
                .externalSessionId(channelSession.getExternalId())
                .text(text)
                .build();
        ChannelType channelType = channelSession.getChannelType();
        queueService.sendMessage(channelType.getChannelServiceType(), Topics.outboundChannelMessages(channelType), message, message.getChannelId());

        ChannelMessage channelMessage = new ChannelMessage();
        channelMessage.setChannelSessionId(channelSession.getId());
        channelMessage.setSenderId(sender.getId());
        channelMessage.setMessageType(ChannelMessageType.OUTBOUND);
        channelMessage.setText(text);
        channelMessage = channelMessageService.createChannelMessage(channelMessage);

        channelMessageSubscriptionService.handleNewMessage(channelSession, channelMessage);
    }

}
