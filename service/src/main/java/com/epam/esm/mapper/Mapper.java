package com.epam.esm.mapper;

import java.util.List;

public interface Mapper <E, D>{
    D mapEntityToDto(E entity);
    E mapDtoToEntity(D dto);
    List<D> mapEntityListToDtoList(List<E> entities);
    List<E> mapDtoListToEntityList(List<D> dtoList);
}
