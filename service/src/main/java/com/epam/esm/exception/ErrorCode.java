package com.epam.esm.exception;

public enum ErrorCode {

    GIFT_CERTIFICATE_NOT_FOUND("gc_01"),
    GC_NAME_NOT_CORRECT("gc_02"),
    GC_DESCRIPTION_NOT_CORRECT("gc_03"),
    GC_PRICE_NOT_CORRECT("gc_04"),
    GC_DURATION_NOT_CORRECT("gc_05"),
    GC_CREATE_FAILED("gc_06"),
    GC_UPDATE_FAILED("gc_07"),
    GC_NAME_EMPTY("gc_08"),
    GC_DESCRIPTION_EMPTY("gc_09"),
    GC_PRICE_EMPTY("gc_10"),
    GC_DURATION_EMPTY("gc_11"),
    GC_ID_EMPTY("gc_12"),
    GC_ID_INCORRECT("gc_13"),

    TAG_NOT_FOUND("tg_01"),
    TAG_NAME_NOT_CORRECT("tg_02"),
    TAG_CREATE_FAILED("tg_03"),
    TAG_EXISTS("tg_04"),
    TAG_NAME_EMPTY("tg_05"),

    ORDER_NOT_FOUND("or_01"),
    ORDER_CREATE_FAILED("or_02"),
    ORDER_EMPTY_CERTIFICATES("or_03"),

    USER_NOT_FOUND("us_01"),
    USER_ID_INCORRECT("us_03"),
    USER_ID_EMPTY("us_03"),

    INCORRECT_PAGE_SIZE("pg_01"),
    INCORRECT_PAGE_NUMBER("pg_02"),
    PAGE_NOT_FOUND("pg_03");

    private final String errorCode;

    ErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
