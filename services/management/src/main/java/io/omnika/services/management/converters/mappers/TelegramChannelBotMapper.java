package io.omnika.services.management.converters.mappers;

import io.omnika.common.rest.services.management.dto.channel.TelegramBotChannelDto;
import io.omnika.services.management.core.converter.mapper.BasicMapper;
import io.omnika.services.management.model.channel.TelegramBotChannel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TelegramChannelBotMapper extends BasicMapper<TelegramBotChannel, TelegramBotChannelDto> {

    TelegramChannelBotMapper INSTANCE = Mappers.getMapper(TelegramChannelBotMapper.class);

    @Override
    @Mapping(target = "channel",
            expression = "java(ChannelMapper.INSTANCE.toDomain(telegramBotChannelDto))")
    TelegramBotChannel toDomain(TelegramBotChannelDto telegramBotChannelDto);

    @Override
    @Mapping(target = "name", expression = "java(telegramBotChannel.getChannel().getName())")
    @Mapping(target = "channelType", expression = "java(telegramBotChannel.getChannel().getChannelType())")
    @Mapping(target = "tenantDto", expression = "java(TenantMapper.INSTANCE.toDto(telegramBotChannel.getChannel().getTenant()))")
    TelegramBotChannelDto toDto(TelegramBotChannel telegramBotChannel);

}
