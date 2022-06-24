package io.omnika.common.rest.services.management.dto.channel;

import io.omnika.common.rest.services.management.model.ChannelType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InstagramChannelConfig implements ChannelConfig {

    @NotNull
    @NotBlank
    private String username;

    // TODO: pay attention, there is real password, so should be encoded
    @NotNull
    @NotBlank
    private String password;

    @Override
    public ChannelType getType() {
        return ChannelType.INSTAGRAM;
    }
}
