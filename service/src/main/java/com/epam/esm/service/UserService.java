package com.epam.esm.service;

import com.epam.esm.dto.OrderDto;

import java.util.List;

public interface UserService {
    List<OrderDto> findUserOrders(Long userId, int page, int size);
}
