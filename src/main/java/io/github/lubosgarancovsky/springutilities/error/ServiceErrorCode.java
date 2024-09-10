package io.github.lubosgarancovsky.springutilities.error;

import static io.github.lubosgarancovsky.springutilities.error.ErrorCodeType.INTERNAL;

public enum ServiceErrorCode implements ErrorCode {
    UNKNOWN_SERVICE_ERROR(INTERNAL, "Please contact technical support.");

    private final ErrorCodeType type;
    private final String template;

    ServiceErrorCode(ErrorCodeType type, String template) {
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