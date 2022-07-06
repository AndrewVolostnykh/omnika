package io.omnika.common.model.channel;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "type", visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = TelegramBotChannelConfig.class, name = "TELEGRAM_BOT"),
        @JsonSubTypes.Type(value = ViberBotChannelConfig.class, name = "VIBER_BOT"),
        @JsonSubTypes.Type(value = InstagramChannelConfig.class, name = "INSTAGRAM")
})
public interface ChannelConfig {

    ChannelType getType();

}
