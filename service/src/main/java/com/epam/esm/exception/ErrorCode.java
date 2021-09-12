package com.epam.esm.exception;

public enum ErrorCode {
    GIFT_CERTIFICATE_NOT_FOUND("gc_01"),
    TAG_NOT_FOUND("tg_01"),
    GC_NAME_NOT_CORRECT("gc_02"),
    GC_DESCRIPTION_CORRECT("gc_03"),
    GC_PRICE_NOT_CORRECT("gc_04"),
    GC_DURATION_NOT_CORRECT("gc_05"),
    TAG_EXISTS("tg_02");

    private final String errorCode;

    ErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
