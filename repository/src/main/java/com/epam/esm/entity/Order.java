package com.epam.esm.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Order {
    private long id;
    private long userId;
    private LocalDateTime purchaseDate;
    private BigDecimal cost;

    public Order(){}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public LocalDateTime getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(LocalDateTime purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", userId=" + userId +
                ", purchaseDate=" + purchaseDate +
                ", cost=" + cost +
                '}';
    }
}
