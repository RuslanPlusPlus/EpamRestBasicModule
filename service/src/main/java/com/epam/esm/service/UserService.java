package com.epam.esm.service;

import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.UserDto;

import java.util.List;

public interface UserService {
    List<OrderDto> findUserOrders(Long userId, int page, int size);
    List<UserDto> findAll(int page, int size);
    UserDto findById(Long id);
    long countPages(int pageSize);
    long countUserOrdersPages(Long id, int pageSize);
}
