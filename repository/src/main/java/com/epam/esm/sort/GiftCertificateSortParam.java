package com.epam.esm.sort;

public enum GiftCertificateSortParam {
    NAME("name"),
    PRICE("price"),
    DURATION("duration"),
    CREATEDATE("createDate");

    private final String value;

    GiftCertificateSortParam(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

}
