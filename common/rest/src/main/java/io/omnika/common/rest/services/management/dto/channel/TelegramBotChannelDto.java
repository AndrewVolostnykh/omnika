package io.omnika.common.rest.services.management.dto.channel;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class TelegramBotChannelDto extends ChannelDto {

    @NotNull
    private String botName;

    @NotNull
    private String apiKey;
}
