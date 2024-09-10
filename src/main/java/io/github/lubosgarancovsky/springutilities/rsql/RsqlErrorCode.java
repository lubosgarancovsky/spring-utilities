package io.github.lubosgarancovsky.springutilities.rsql;

import io.github.lubosgarancovsky.springutilities.error.ErrorCode;
import io.github.lubosgarancovsky.springutilities.error.ErrorCodeType;

public enum RsqlErrorCode implements ErrorCode {
    INVALID_RSQL_BOOLEAN_VALUE_EXCEPTION("Incorrect boolean value '%s' inside RSQL condition."),
    INVALID_RSQL_DATETIME_VALUE_EXCEPTION("Incorrect datetime value '%s' inside RSQL condition."),
    INVALID_RSQL_UUID_VALUE_EXCEPTION("Incorrect UUID value '%s' inside RSQL condition."),
    INVALID_RSQL_VALUE_EXCEPTION("Incorrect value '%s' inside RSQL condition."),
    INVALID_RSQL_OPERATOR("Not supported operator '%s' for field '%s' inside RSQL condition."),
    PARSE_RSQL_EXCEPTION("Rsql exception was raised while parsing.");

    final String template;

    RsqlErrorCode(String template) {
        this.template = template;
    }

    @Override
    public String template() {
        return template;
    }

    @Override
    public ErrorCodeType type() {
        return ErrorCodeType.BAD_REQUEST;
    }
}
