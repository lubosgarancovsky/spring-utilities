package io.github.lubosgarancovsky.springutilities.error;

import java.util.List;

public class BusinessException extends RuntimeException {

    private final BusinessError businessError;

    public BusinessException(String message, BusinessError businessError) {
        super(message, businessError.getCause());
        this.businessError = businessError;
    }

    public BusinessError getError() {
        return businessError;
    }

    public List<String> params() {
        return businessError.params();
    }
}
