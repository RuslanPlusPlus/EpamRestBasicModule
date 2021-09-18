package com.epam.esm.mapper.impl;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import com.epam.esm.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderMapper implements Mapper<Order, OrderDto> {

    private final Mapper<GiftCertificate, GiftCertificateDto> certificateMapper;

    @Autowired
    public OrderMapper(Mapper<GiftCertificate, GiftCertificateDto> certificateMapper){
        this.certificateMapper = certificateMapper;
    }

    @Override
    public OrderDto mapEntityToDto(Order entity) {
        OrderDto orderDto = new OrderDto();
        orderDto.setId(entity.getId());
        orderDto.setCost(entity.getCost());
        orderDto.setCreateDate(entity.getCreateDate());
        orderDto.setUserId(entity.getUser().getId());
        if (!entity.getGiftCertificates().isEmpty()){
            List<GiftCertificateDto> giftCertificateDtoList =
                    entity.getGiftCertificates().stream()
                    .map(certificateMapper::mapEntityToDto)
                    .collect(Collectors.toList());
            orderDto.setGiftCertificateDtoList(giftCertificateDtoList);
        }
        return orderDto;
    }

    @Override
    public Order mapDtoToEntity(OrderDto dto) {
        Order order = new Order();
        order.setId(dto.getId());
        order.setCost(dto.getCost());
        order.setCreateDate(dto.getCreateDate());
        User user = new User();
        user.setId(dto.getUserId());
        order.setUser(user);
        if (!dto.getGiftCertificateDtoList().isEmpty()){
            List<GiftCertificate> giftCertificateList =
                    dto.getGiftCertificateDtoList().stream()
                    .map(certificateMapper::mapDtoToEntity)
                    .collect(Collectors.toList());
            order.setGiftCertificates(giftCertificateList);
        }
        return order;
    }

    @Override
    public List<OrderDto> mapEntityListToDtoList(List<Order> entities) {
        return entities.stream()
                .map(this::mapEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<Order> mapDtoListToEntityList(List<OrderDto> dtoList) {
        return dtoList.stream()
                .map(this::mapDtoToEntity)
                .collect(Collectors.toList());
    }
}
