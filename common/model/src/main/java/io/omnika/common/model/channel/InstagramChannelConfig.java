package io.omnika.common.model.channel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

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
