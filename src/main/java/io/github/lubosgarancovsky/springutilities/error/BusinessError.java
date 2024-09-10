package io.github.lubosgarancovsky.springutilities.error;

import org.immutables.value.Value;

import java.util.Arrays;
import java.util.List;

import io.github.lubosgarancovsky.springutilities.error.ImmutableBusinessError;


@Value.Immutable
public abstract class BusinessError extends Throwable {
    @Value.Parameter
    public abstract ErrorCode code();

    @Value.Parameter
    public abstract List<String> params();

    public abstract List<NestedBusinessError> nestedErrors();

    @Value.Derived
    public String message() {
        return String.format(this.code().template(), this.params().toArray());
    }

    public static BusinessError of(ErrorCode errorCode, String... params) {
        return ImmutableBusinessError.of(errorCode, Arrays.asList(params));
    }

    public static BusinessError of(ErrorCode errorCode, List<String> params) {
        return ImmutableBusinessError.of(errorCode, params);
    }

    public BusinessError addNestedErrors(List<NestedBusinessError> params) {
        return ((ImmutableBusinessError) (this)).withNestedErrors(params);
    }

    public BusinessError addNestedErrors(NestedBusinessError... params) {
        return addNestedErrors(Arrays.asList(params));
    }

    public BusinessException convertToException() {
        return new BusinessException(message(), this);
    }

    @Override
    public String getMessage() {
        return message();
    }

    @Override
    public String toString() {
        return message();
    }

}
