package com.epam.esm.dao;

import com.epam.esm.entity.Order;

import java.util.List;

public interface OrderDao extends BaseDao<Order>{
    List<Order> findAll(int page, int size, List<String> sortParams);
}
