package com.epam.esm.sort;

public enum TagSortParam {
    NAME("name");

    private final String value;

    TagSortParam(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
