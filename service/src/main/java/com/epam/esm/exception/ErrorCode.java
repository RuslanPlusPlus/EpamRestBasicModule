package com.epam.esm.exception;

public enum ErrorCode {
    GIFT_CERTIFICATE_NOT_FOUND("gc_01"),
    TAG_NOT_FOUND("tg_01");

    private final String errorCode;

    ErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
