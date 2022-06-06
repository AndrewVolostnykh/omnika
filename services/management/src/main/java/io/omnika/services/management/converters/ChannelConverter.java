package io.omnika.services.management.converters;

import io.omnika.common.exceptions.ObjectNotFoundException;
import io.omnika.common.rest.services.management.dto.channel.ChannelDto;
import io.omnika.services.management.converters.mappers.ChannelMapper;
import io.omnika.services.management.model.channel.Channel;
import io.omnika.services.management.model.channel.TelegramBotChannel;
import io.omnika.services.management.repository.channel.TelegramBotChannelRepository;
import javax.naming.OperationNotSupportedException;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

@Component
public class ChannelConverter extends AbstractBasicConverter<Channel, ChannelDto> {

    private final TelegramBotChannelRepository telegramBotChannelRepository;
    private final TelegramBotChannelConverter telegramBotChannelConverter;

    public ChannelConverter(TelegramBotChannelRepository telegramBotChannelRepository, TelegramBotChannelConverter telegramBotChannelConverter) {
        super(ChannelMapper.INSTANCE);
        this.telegramBotChannelRepository = telegramBotChannelRepository;
        this.telegramBotChannelConverter = telegramBotChannelConverter;
    }

    @Override
    @SneakyThrows
    public Channel toDomain(ChannelDto dto) {
        throw new OperationNotSupportedException();
    }

    // FIXME: change to database view, because it takes too long time to convert every entity to needed type
    @Override
    public ChannelDto toDto(Channel domain) {
        switch (domain.getChannelType()) {
            case TELEGRAM_BOT:
                return telegramBotChannelConverter.toDto(
                        telegramBotChannelRepository.findById(domain.getId())
                        .orElseThrow(() -> new ObjectNotFoundException(domain.getId(), TelegramBotChannel.class))
                );
            case VIBER_BOT:
                return null;
            default: throw new IllegalArgumentException("Unsupported channel type: " + domain.getChannelType());
        }
    }
}
