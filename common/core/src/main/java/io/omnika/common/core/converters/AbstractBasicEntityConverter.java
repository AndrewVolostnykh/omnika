package io.omnika.common.core.converters;

public abstract class AbstractBasicEntityConverter<D, T> {

    protected final BasicEntityMapper<D, T> mapper;

    protected AbstractBasicEntityConverter(BasicEntityMapper<D, T> mapper) {
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
