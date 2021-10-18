package com.epam.esm.mapper.impl;

import com.epam.esm.dto.RefreshTokenDto;
import com.epam.esm.entity.RefreshToken;
import com.epam.esm.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RefreshTokenMapper implements Mapper<RefreshToken, RefreshTokenDto> {

    private final UserMapper userMapper;

    @Autowired
    public RefreshTokenMapper(UserMapper userMapper){
        this.userMapper = userMapper;
    }

    @Override
    public RefreshTokenDto mapEntityToDto(RefreshToken entity) {
        RefreshTokenDto tokenDto = new RefreshTokenDto();
        tokenDto.setId(entity.getId());
        tokenDto.setToken(entity.getToken());
        tokenDto.setUser(userMapper.mapEntityToDto(entity.getUser()));
        tokenDto.setExpireDate(entity.getExpireDate());
        return tokenDto;
    }

    @Override
    public RefreshToken mapDtoToEntity(RefreshTokenDto dto) {
        RefreshToken token = new RefreshToken();
        token.setId(dto.getId());
        token.setToken(dto.getToken());
        token.setUser(userMapper.mapDtoToEntity(dto.getUser()));
        token.setExpireDate(dto.getExpireDate());
        return token;
    }

    @Override
    public List<RefreshTokenDto> mapEntityListToDtoList(List<RefreshToken> entities) {
        return entities.stream()
                .map(this::mapEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<RefreshToken> mapDtoListToEntityList(List<RefreshTokenDto> dtoList) {
        return dtoList.stream()
                .map(this::mapDtoToEntity)
                .collect(Collectors.toList());
    }
}
