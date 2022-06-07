package io.omnika.services.management.repository.channel;

import io.omnika.services.management.model.channel.TelegramBotChannel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TelegramBotChannelRepository extends JpaRepository<TelegramBotChannel, Long> {

}
