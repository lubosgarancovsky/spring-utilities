package io.github.lubosgarancovsky.springutilities.error;

import java.util.List;

import org.immutables.value.Value;

@Value.Immutable
public abstract class NestedBusinessError {


    @Value.Parameter
    public abstract String serviceId();


    @Value.Parameter
    public abstract String faultCode();


    @Value.Parameter
    public abstract String faultMessage();


    @Value.Parameter
    public abstract List<String> faultMessageParams();
}
