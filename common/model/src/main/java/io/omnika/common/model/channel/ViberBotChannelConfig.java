package io.omnika.common.model.channel;

import lombok.Data;

@Data
public class ViberBotChannelConfig implements ChannelConfig {

    private String authToken;

    @Override
    public ChannelType getType() {
        return ChannelType.VIBER_BOT;
    }
}
