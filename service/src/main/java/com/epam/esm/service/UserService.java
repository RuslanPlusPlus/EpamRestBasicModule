package com.epam.esm.service;

import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<OrderDto> findUserOrders(Long userId, int page, int size);
    List<UserDto> findAll(int page, int size, List<String> sortParams);
    UserDto findById(Long id);
    long countPages(int pageSize);
    long countUserOrdersPages(Long id, int pageSize);
    UserDto save(UserDto user);
    UserDto findByUsername(String username);
}
