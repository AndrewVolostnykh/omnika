package io.omnika.services.management.converters;

import io.omnika.services.management.core.converter.mapper.BasicMapper;

public abstract class AbstractBasicConverter<D, T> {

    protected final BasicMapper<D, T> mapper;

    protected AbstractBasicConverter(BasicMapper<D, T> mapper) {
        this.mapper = mapper;
    }

    public D toDomain(T dto) {
        return mapper.toDomain(dto);
    }

    public T toDto(D domain) {
        return mapper.toDto(domain);
    }

    // maybe there also collection based toDomain and toDto methods add ?
}
