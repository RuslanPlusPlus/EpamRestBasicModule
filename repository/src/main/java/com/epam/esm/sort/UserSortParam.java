package com.epam.esm.sort;

public enum UserSortParam {
    NAME("name");

    private final String value;

    UserSortParam(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
