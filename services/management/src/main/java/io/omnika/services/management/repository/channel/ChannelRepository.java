package io.omnika.services.management.repository.channel;

import io.omnika.common.rest.services.management.model.ChannelType;
import io.omnika.services.management.model.channel.Channel;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChannelRepository extends JpaRepository<Channel, Long> {

    List<Channel> findAllByChannelType(ChannelType channelType);
}
