package com.epam.esm.service.impl;

import com.epam.esm.dao.UserDao;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import com.epam.esm.exception.*;
import com.epam.esm.mapper.impl.OrderMapper;
import com.epam.esm.mapper.impl.UserMapper;
import com.epam.esm.service.UserService;
import com.epam.esm.validator.PaginationValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final UserMapper userMapper;
    private final OrderMapper orderMapper;
    private final PaginationValidator paginationValidator;

    @Autowired
    public UserServiceImpl (UserDao userDao,
                            UserMapper userMapper,
                            OrderMapper orderMapper,
                            PaginationValidator paginationValidator){
        this.userDao = userDao;
        this.userMapper = userMapper;
        this.orderMapper = orderMapper;
        this.paginationValidator = paginationValidator;
    }

    @Override
    @Transactional
    public List<OrderDto> findUserOrders(Long userId, int page, int size) {
        // TODO: 19.09.2021 validate id
        paginationValidator.validatePageNumber(page);
        paginationValidator.validateSize(size);
        return orderMapper.mapEntityListToDtoList(userDao.findOrdersByUserId(userId, page, size));
    }

    @Override
    @Transactional
    public List<UserDto> findAll(int page, int size, List<String> sortParams) {
        paginationValidator.validatePageNumber(page);
        paginationValidator.validateSize(size);
        List<User> users = userDao.findAll(page, size, sortParams);
        return userMapper.mapEntityListToDtoList(users);
    }

    @Override
    public UserDto findById(Long id) {
        // TODO: 19.09.2021 validate id
        Optional<User> optionalUser = userDao.findById(id);
        if (optionalUser.isEmpty()){
            ExceptionDetail exceptionDetail = new ExceptionDetail(
                    ResponseMessage.RESOURCE_NOT_FOUND_BY_ID,
                    ErrorCode.USER_NOT_FOUND.getErrorCode(),
                    String.valueOf(id)
            );
            throw new ResourceNotFoundException(exceptionDetail);
        }
        return userMapper.mapEntityToDto(optionalUser.get());
    }

    @Override
    public long countPages(int pageSize) {
        paginationValidator.validateSize(pageSize);
        long recordsAmount = userDao.findRecordsAmount();
        long pageAmount = recordsAmount % pageSize == 0 ? recordsAmount / pageSize : recordsAmount / pageSize + 1;
        pageAmount = pageAmount == 0 ? 1 : pageAmount;
        return pageAmount;
    }

    @Override
    public long countUserOrdersPages(Long id, int pageSize) {
        paginationValidator.validateSize(pageSize);
        Optional<User> optionalUser = userDao.findById(id);
        if (optionalUser.isEmpty()) {
            ExceptionDetail exceptionDetail = new ExceptionDetail(
                    ResponseMessage.RESOURCE_NOT_FOUND_BY_ID,
                    ErrorCode.USER_NOT_FOUND.getErrorCode(),
                    String.valueOf(id)
            );
            throw new ResourceNotFoundException(exceptionDetail);
        }
        Set<Order> orders = optionalUser.get().getOrders();
        long recordsAmount = orders == null? 0 : orders.size();
        long pageAmount = recordsAmount % pageSize == 0 ? recordsAmount / pageSize : recordsAmount / pageSize + 1;
        pageAmount = pageAmount == 0 ? 1 : pageAmount;
        return pageAmount;
    }
}
