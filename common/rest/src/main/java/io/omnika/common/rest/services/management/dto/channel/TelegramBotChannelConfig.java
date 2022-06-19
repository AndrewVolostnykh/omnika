package io.omnika.common.rest.services.management.dto.channel;

import io.omnika.common.rest.services.management.model.ChannelType;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TelegramBotChannelConfig implements ChannelConfig {

    @NotNull
    private String botName;

    @NotNull
    private String apiKey;

    @Override
    public ChannelType getType() {
        return ChannelType.TELEGRAM_BOT;
    }
}
