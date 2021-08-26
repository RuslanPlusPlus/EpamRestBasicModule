package com.epam.esm.mapper;

import java.util.List;

// TODO: 26.08.2021 refactor with spring model mapper 
public interface Mapper <E, D>{
    D mapEntityToDto(E entity);
    E mapDtoToEntity(D dto);
    List<D> mapEntityListToDtoList(List<E> entities);
    List<E> mapDtoListToEntityList(List<D> dtoList);
}
