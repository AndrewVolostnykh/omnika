package io.omnika.services.telegram.channel.service.saga;

import io.omnika.common.ipc.streams.SendToStream;
import io.omnika.common.ipc.streams.saga.Saga;
import io.omnika.common.rest.services.channels.dto.ChannelMessageDto;
import org.springframework.stereotype.Component;

@Component
public class SendMessageToGatewaySaga implements Saga<ChannelMessageDto, ChannelMessageDto> {

    private static final String SEND_MESSAGE_EXCHANGE = "message";

    @SendToStream(exchange = SEND_MESSAGE_EXCHANGE)
    public ChannelMessageDto send(ChannelMessageDto channelMessageDto) {
        return channelMessageDto;
    }
}
