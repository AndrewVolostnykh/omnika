package io.omnika.services.messaging.gateway.model;

import io.omnika.common.rest.services.management.model.ChannelType;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "channel_sessions")
public class ChannelSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long sessionId;

    private Long channelId;

    private Long tenantId;

    @Enumerated(EnumType.STRING)
    private ChannelType channelType;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "channelSession")
    private List<ChannelMessage> messages = new ArrayList<>();

}
