package io.omnika.services.management.service;

import io.omnika.common.rest.services.management.dto.channel.TelegramBotChannelDto;
import io.omnika.services.management.converters.TelegramBotChannelConverter;
import io.omnika.services.management.core.service.TelegramChannelBotService;
import io.omnika.services.management.model.channel.TelegramBotChannel;
import io.omnika.services.management.repository.channel.ChannelRepository;
import io.omnika.services.management.repository.channel.TelegramBotChannelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class TelegramBotChannelServiceImpl implements TelegramChannelBotService {

    private final ChannelRepository channelRepository;
    private final TelegramBotChannelRepository telegramBotChannelRepository;
    private final TelegramBotChannelConverter telegramBotChannelConverter;

    @Override
    public TelegramBotChannelDto create(TelegramBotChannelDto telegramBotChannelDto) {
        // validators
        if (telegramBotChannelDto.getTenantDto().getId() == null) {
            throw new IllegalArgumentException("Tenant id should be specified on creating telegram bot channel");
        }

        TelegramBotChannel telegramBotChannel = telegramBotChannelConverter.toDomain(telegramBotChannelDto);
        channelRepository.save(telegramBotChannel.getChannel());
        TelegramBotChannel saved = telegramBotChannelRepository.save(telegramBotChannel);
        return telegramBotChannelConverter.toDto(saved);
    }
}
