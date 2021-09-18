package com.epam.esm.mapper.impl;

import com.epam.esm.dto.UserDto;
import com.epam.esm.entity.User;
import com.epam.esm.mapper.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserMapper implements Mapper<User, UserDto> {
    @Override
    public UserDto mapEntityToDto(User entity) {
        return null;
    }

    @Override
    public User mapDtoToEntity(UserDto dto) {
        return null;
    }

    @Override
    public List<UserDto> mapEntityListToDtoList(List<User> entities) {
        return null;
    }

    @Override
    public List<User> mapDtoListToEntityList(List<UserDto> dtoList) {
        return null;
    }
}
