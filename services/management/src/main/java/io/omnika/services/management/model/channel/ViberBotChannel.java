package io.omnika.services.management.model.channel;

import io.omnika.common.rest.services.management.model.ChannelType;
import io.omnika.services.management.core.model.channel.ChannelTypeAware;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "viber_bot_channels")
@NoArgsConstructor
public class ViberBotChannel implements ChannelTypeAware {

    @Id
    private Long id;

    private String authToken;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    private Channel channel;

    @Override
    public ChannelType getType() {
        return ChannelType.VIBER_BOT;
    }
}
