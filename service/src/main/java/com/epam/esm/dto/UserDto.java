package com.epam.esm.dto;

import java.util.ArrayList;
import java.util.List;

public class UserDto {
    private Long id;
    private String name;
    private List<OrderDto> orderDtoList = new ArrayList<>();

    public UserDto() {
    }

    public UserDto(Long id, String name, List<OrderDto> orderDtoList) {
        this.id = id;
        this.name = name;
        this.orderDtoList = orderDtoList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<OrderDto> getOrderDtoList() {
        return orderDtoList;
    }

    public void setOrderDtoList(List<OrderDto> orderDtoList) {
        this.orderDtoList = orderDtoList;
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", orderDtoList=" + orderDtoList +
                '}';
    }
}
