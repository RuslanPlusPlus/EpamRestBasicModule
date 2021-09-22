package com.epam.esm.service;

import com.epam.esm.dto.OrderDto;

import java.util.List;

public interface OrderService {
    OrderDto save(OrderDto orderDto);
    List<OrderDto> findAll(int page, int size, List<String> sortParams);
    OrderDto findById(Long id);
    long countPages(int pageSize);
    void delete(Long id);
}
