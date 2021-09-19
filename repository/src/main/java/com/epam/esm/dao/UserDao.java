package com.epam.esm.dao;

import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;

import java.util.List;

public interface UserDao extends BaseDao<User>{
    List<Order> findOrdersByUserId(Long userId, int page, int size);
}
