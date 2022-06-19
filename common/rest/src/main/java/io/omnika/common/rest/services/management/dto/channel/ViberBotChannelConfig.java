package io.omnika.common.rest.services.management.dto.channel;

import io.omnika.common.rest.services.management.model.ChannelType;
import lombok.Data;

@Data
public class ViberBotChannelConfig implements ChannelConfig {

    private String authToken;

    @Override
    public ChannelType getType() {
        return ChannelType.VIBER_BOT;
    }
}
