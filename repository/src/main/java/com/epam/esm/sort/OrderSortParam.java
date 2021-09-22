package com.epam.esm.sort;

public enum OrderSortParam {
    COST("cost"),
    CREATEDATE("createDate");

    private final String value;

    OrderSortParam(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
