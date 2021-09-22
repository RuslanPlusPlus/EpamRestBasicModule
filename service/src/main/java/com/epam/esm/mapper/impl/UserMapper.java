package com.epam.esm.mapper.impl;

import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import com.epam.esm.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UserMapper implements Mapper<User, UserDto> {

    private final OrderMapper orderMapper;

    @Autowired
    public UserMapper (OrderMapper orderMapper){
        this.orderMapper = orderMapper;
    }

    @Override
    public UserDto mapEntityToDto(User entity) {
        UserDto userDto = new UserDto();
        userDto.setId(entity.getId());
        userDto.setName(entity.getName());
        if (!entity.getOrders().isEmpty()){
            List<OrderDto> orderDtoList =
                    entity.getOrders().stream()
                    .map(orderMapper::mapEntityToDto)
                    .collect(Collectors.toList());
            userDto.setOrderDtoList(orderDtoList);
        }
        return userDto;
    }

    @Override
    public User mapDtoToEntity(UserDto dto) {
        User user = new User();
        user.setId(dto.getId());
        user.setName(dto.getName());
        if (!dto.getOrderDtoList().isEmpty()){
            Set<Order> orderSet =
                    dto.getOrderDtoList().stream()
                    .map(orderMapper::mapDtoToEntity)
                    .collect(Collectors.toSet());
            user.setOrders(orderSet);
        }
        return user;
    }

    @Override
    public List<UserDto> mapEntityListToDtoList(List<User> entities) {
        return entities.stream()
                .map(this::mapEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<User> mapDtoListToEntityList(List<UserDto> dtoList) {
        return dtoList.stream()
                .map(this::mapDtoToEntity)
                .collect(Collectors.toList());
    }
}
