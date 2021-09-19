package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.OrderDao;
import com.epam.esm.dao.UserDao;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import com.epam.esm.exception.*;
import com.epam.esm.mapper.impl.OrderMapper;
import com.epam.esm.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderDao orderDao;
    private final UserDao userDao;
    private final OrderMapper orderMapper;
    private final GiftCertificateDao certificateDao;

    @Autowired
    public OrderServiceImpl(OrderDao orderDao,
                            OrderMapper orderMapper,
                            UserDao userDao,
                            GiftCertificateDao certificateDao){
        this.orderDao = orderDao;
        this.orderMapper = orderMapper;
        this.userDao = userDao;
        this.certificateDao = certificateDao;
    }

    @Override
    @Transactional
    public OrderDto save(OrderDto orderDto) {
        // TODO: 18.09.2021 validate userId
        // TODO: 18.09.2021 validate order
        Optional<User> optionalUser = userDao.findById(orderDto.getUserId());
        if (optionalUser.isEmpty()){
            // TODO: 18.09.2021 throw exception
        }
        Order order = orderMapper.mapDtoToEntity(orderDto);
        order.setUser(optionalUser.get());
        List<GiftCertificate> giftCertificateList =
                orderDto.getCertificates().stream()
                .map(certificateDto -> {
                    if (certificateDao.findById(certificateDto.getId()).isEmpty()){
                        // TODO: 18.09.2021 throw exception
                    }
                    return certificateDao.findById(certificateDto.getId()).get();
                }).collect(Collectors.toList());
        order.setGiftCertificates(giftCertificateList);
        order.setCost(
                order.getGiftCertificates().stream().
                        map(GiftCertificate::getPrice).
                        reduce(BigDecimal.ZERO, BigDecimal::add)
        );
        order.setCreateDate(LocalDateTime.now());
        Optional<Order> orderSaved = orderDao.save(order);
        if (orderSaved.isEmpty()){
            // TODO: 18.09.2021 throw exception
        }
        return orderMapper.mapEntityToDto(orderSaved.get());
    }

    @Override
    @Transactional
    public List<OrderDto> findAll(int page, int size) {
        List<Order> orders = orderDao.findAll(page, size);
        return orderMapper.mapEntityListToDtoList(orders);
    }

    @Override
    @Transactional
    public OrderDto findById(Long id) {
        // TODO: 19.09.2021 validate id
        Optional<Order> optionalTag = orderDao.findById(id);
        if (optionalTag.isEmpty()){
            ExceptionDetail exceptionDetail = new ExceptionDetail(
                    ResponseMessage.RESOURCE_NOT_FOUND_BY_ID,
                    ErrorCode.ORDER_NOT_FOUND.getErrorCode(),
                    String.valueOf(id)
            );
            throw new AppException(exceptionDetail);
        }
        return orderMapper.mapEntityToDto(optionalTag.get());
    }

    @Override
    @Transactional
    public long countPages(int pageSize) {
        // TODO: 18.09.2021 validate size
        long recordsAmount = orderDao.findRecordsAmount();
        long pageAmount = recordsAmount % pageSize == 0 ? recordsAmount / pageSize : recordsAmount / pageSize + 1;
        pageAmount = pageAmount == 0 ? 1 : pageAmount;
        return pageAmount;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        // TODO: 19.09.2021 validate id
        Optional<Order> optionalOrder = orderDao.findById(id);
        if (optionalOrder.isEmpty()){
            ExceptionDetail exceptionDetail = new ExceptionDetail(
                    ResponseMessage.RESOURCE_NOT_FOUND_BY_ID,
                    ErrorCode.ORDER_NOT_FOUND.getErrorCode(),
                    String.valueOf(id)
            );
            throw new AppException(exceptionDetail);
        }
        Order order = optionalOrder.get();
        Optional<User> optionalUser = userDao.findById(order.getUser().getId());
        if (optionalUser.isEmpty()){
            ExceptionDetail exceptionDetail = new ExceptionDetail(
                    ResponseMessage.RESOURCE_NOT_FOUND_BY_ID,
                    ErrorCode.USER_NOT_FOUND.getErrorCode(),
                    String.valueOf(id)
            );
            throw new AppException(exceptionDetail);
        }
        optionalUser.get().getOrders().remove(order);
        orderDao.delete(id);
    }
}
