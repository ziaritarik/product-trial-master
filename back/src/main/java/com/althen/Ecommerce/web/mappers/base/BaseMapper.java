package com.althen.Ecommerce.web.mappers.base;


import java.util.Objects;
import java.util.stream.Stream;

public abstract class BaseMapper<T, D> {

    public Stream<T> toEntities(Stream<D> dtos) {
        return dtos.map(this::mapToEntity).filter(Objects::nonNull);
    }

    public Stream<D> toDTOs(Stream<T> entities) {
        return entities.map(this::mapToDTO).filter(Objects::nonNull);
    }
    public abstract T mapToEntity(D dto);

    public abstract D mapToDTO(T entity);

 }
