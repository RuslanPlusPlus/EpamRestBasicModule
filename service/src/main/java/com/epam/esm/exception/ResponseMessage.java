package com.epam.esm.exception;

public class ResponseMessage {
    public static final String RESOURCE_NOT_FOUND_BY_ID = "message.resource_not_found_by_id";
    public static final String SUCH_TAG_NOT_FOUND = "message.such_tag_not_found";
    public static final String INTERNAL_SERVER_ERROR = "message.internal_server_error";
    public static final String INCORRECT_NAME = "message.incorrect_name";
    public static final String EMPTY_NAME = "message.empty_name";
    public static final String INCORRECT_DESCRIPTION = "message.incorrect_description";
    public static final String EMPTY_DESCRIPTION = "message.empty_description";
    public static final String INCORRECT_PRICE= "message.incorrect_price";
    public static final String EMPTY_PRICE = "message.empty_price";
    public static final String INCORRECT_DURATION= "message.incorrect_duration";
    public static final String EMPTY_DURATION = "message.empty_duration";
    public static final String TAG_NAME_EXISTS = "message.tag_name_exists";
    public static final String FAILED_TO_CREATE = "message.create_failed";
    public static final String FAILED_TO_UPDATE = "message.create_failed";
    public static final String INCORRECT_PAGE_SIZE = "message.incorrect_page_size";
    public static final String INCORRECT_PAGE_NUMBER = "message.incorrect_page_number";
    public static final String PAGE_NOT_FOUND = "message.page_not_found";
    public static final String ORDER_CERTIFICATES_EMPTY = "message.page_certificates_empty";
    public static final String EMPTY_USER_ID = "message.empty_user_id";
    public static final String INCORRECT_USER_ID = "message.incorrect_user_id";
    public static final String EMPTY_CERTIFICATE_ID = "message.empty_certificate_id";
    public static final String INCORRECT_CERTIFICATE_ID = "message.incorrect_certificate_id";

    public static final String INVALID_PARAM_FORMAT = "message.invalid_param_format";
    public static final String MESSAGE_NOT_READABLE = "message.http_message_not_readable";
    public static final String METHOD_NOT_ALLOWED = "message.method_not_allowed";
    public static final String MEDIA_NOT_SUPPORTED = "message.unsupported_media_type";

    private ResponseMessage(){}
}
