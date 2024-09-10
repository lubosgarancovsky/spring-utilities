package io.github.lubosgarancovsky.springutilities.error;

public enum BaseApiErrorCode implements ErrorCode {
    BAD_JSON_FORMAT(ErrorCodeType.BAD_REQUEST, "Invalid json format."),
    BAD_REQUEST(ErrorCodeType.BAD_REQUEST, "Invalid request parameters."),
    FORBIDDEN(ErrorCodeType.FORBIDDEN, "That resource data is forbidden."),
    INVALID_ENUM_VALUE(ErrorCodeType.BAD_REQUEST, "Invalid enum value %s. Supported values: %s."),
    INVALID_UUID_VALUE(ErrorCodeType.BAD_REQUEST, "Invalid UUID value %s."),
    METHOD_NOT_ALLOWED(
            ErrorCodeType.METHOD_NOT_ALLOWED, "Method %s not supported. Supported methods: %s."),
    MISSING_HEADER(ErrorCodeType.BAD_REQUEST, "Missing required header %s."),
    NOT_ACCEPTABLE(ErrorCodeType.NOT_ACCEPTABLE, "No content found for negotiation header %s."),
    UNAUTHORIZED(
            ErrorCodeType.UNAUTHORIZED,
            "The credentials are invalid, or access token is missing, expired, revoked, malformed or invalid."),
    INVALID_DATE_FORMAT(ErrorCodeType.BAD_REQUEST, "Invalid date format."),
    VALID_FROM_BEFORE_VALID_THRU(ErrorCodeType.BAD_REQUEST, "ValidFrom must be before validThru."),
    INVALID_JWT(ErrorCodeType.BAD_REQUEST, "Error while parsing JWT, caused by '%s'."),
    INVALID_TYPE_ID(ErrorCodeType.BAD_REQUEST, "Invalid type id property '%s'."),
    MISSING_TYPE_ID(ErrorCodeType.BAD_REQUEST, "Missing type id property."),
    NOT_FOUND(ErrorCodeType.NOT_FOUND, "Resource does not exist."),
    UNSUPPORTED_CONTENT_TYPE(
            ErrorCodeType.BAD_REQUEST,
            "Content-Type '%s' is not supported. Supported content types: %s."),
    JWT_TOKEN_PARSING_ERROR(
            ErrorCodeType.UNAUTHORIZED, "Error while decoding the JWT. Cause exception message: %s"),
    INVALID_ACCEPT_LANGUAGE_HEADER_VALUE(
            ErrorCodeType.BAD_REQUEST, "Invalid Accept-Language header value: %s");

    private final ErrorCodeType type;
    private final String template;

    BaseApiErrorCode(ErrorCodeType type, String template) {
        this.type = type;
        this.template = template;
    }

    @Override
    public String template() {
        return this.template;
    }

    @Override
    public ErrorCodeType type() {
        return this.type;
    }
}
