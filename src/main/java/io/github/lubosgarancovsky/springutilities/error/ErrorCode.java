package io.github.lubosgarancovsky.springutilities.error;

import java.util.stream.Stream;

public interface ErrorCode {

    String template();

    ErrorCodeType type();

    default BusinessError createError(String... params) {
        return BusinessError.of(this, params);
    }

    default BusinessError createError(Object... params) {
        return BusinessError.of(this, Stream.of(params).map(Object::toString).toArray(String[]::new));
    }
}
