package com.epam.esm.service.impl;

import com.epam.esm.dao.UserDao;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.entity.Order;
import com.epam.esm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private UserDao userDao;

    @Autowired
    public UserServiceImpl (UserDao userDao){
        this.userDao = userDao;
    }

    @Override
    public List<OrderDto> findUserOrders(Long userId, int page, int size) {
        List<Order> orders = userDao.findOrdersByUserId(userId);
        System.out.println(orders);
        return null;
    }
}
