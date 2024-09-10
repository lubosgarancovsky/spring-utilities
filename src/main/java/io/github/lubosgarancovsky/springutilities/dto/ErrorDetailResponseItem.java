package io.github.lubosgarancovsky.springutilities.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;
import io.github.lubosgarancovsky.springutilities.dto.ImmutableErrorDetailResponseItem;
import io.github.lubosgarancovsky.springutilities.dto.ImmutableErrorDetailResponseItem;

import java.util.List;

@JsonDeserialize(as = ImmutableErrorDetailResponseItem.class)
@JsonSerialize(as = ImmutableErrorDetailResponseItem.class)
@Value.Immutable
public interface ErrorDetailResponseItem {

    String serviceId();

    String faultCode();

    String faultMessage();

    List<String> faultMessageParams();
}
