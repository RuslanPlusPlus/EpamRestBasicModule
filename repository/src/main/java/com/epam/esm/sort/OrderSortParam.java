package com.epam.esm.sort;

public enum OrderSortParam {
    COST("cost"),
    CREATE_DATE("createDate");

    private final String value;

    OrderSortParam(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
