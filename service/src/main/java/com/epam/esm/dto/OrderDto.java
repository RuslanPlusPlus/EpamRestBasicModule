package com.epam.esm.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OrderDto {
    private Long id;
    private BigDecimal cost;
    private LocalDateTime createDate;
    private Long userId;
    private List<GiftCertificateDto> giftCertificateDtoList = new ArrayList<>();

    public OrderDto() {
    }

    public OrderDto(Long id, BigDecimal cost, LocalDateTime createDate, Long userId, List<GiftCertificateDto> giftCertificateDtoList) {
        this.id = id;
        this.cost = cost;
        this.createDate = createDate;
        this.userId = userId;
        this.giftCertificateDtoList = giftCertificateDtoList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<GiftCertificateDto> getGiftCertificateDtoList() {
        return giftCertificateDtoList;
    }

    public void setGiftCertificateDtoList(List<GiftCertificateDto> giftCertificateDtoList) {
        this.giftCertificateDtoList = giftCertificateDtoList;
    }

    @Override
    public String toString() {
        return "OrderDto{" +
                "id=" + id +
                ", cost=" + cost +
                ", createDate=" + createDate +
                ", userId=" + userId +
                ", giftCertificateDtoList=" + giftCertificateDtoList +
                '}';
    }
}
