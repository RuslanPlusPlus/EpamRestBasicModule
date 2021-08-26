package com.epam.esm.mapper;

public interface Mapper <E, D>{
    D mapEntityToDto(E entity);
    E mapDtoToEntity(D dto);
}
