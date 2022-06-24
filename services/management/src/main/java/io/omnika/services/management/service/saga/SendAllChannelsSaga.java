package io.omnika.services.management.service.saga;

import io.omnika.common.ipc.streams.SendToStream;
import io.omnika.common.ipc.streams.saga.Saga;
import io.omnika.common.rest.services.management.dto.channel.ChannelDto;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class SendAllChannelsSaga implements Saga<List<ChannelDto>, List<ChannelDto>> {
    private static final String ALL_CHANNELS_EXCHANGE = "allChannels";

    @SendToStream(exchange = ALL_CHANNELS_EXCHANGE)
    public List<ChannelDto> send(List<ChannelDto> channelDtos) {
        return channelDtos;
    }
}
