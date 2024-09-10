package io.github.lubosgarancovsky.springutilities.dto;

import org.immutables.value.Value;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.github.lubosgarancovsky.springutilities.dto.ImmutableErrorDetailResponse;

@JsonDeserialize(as = ImmutableErrorDetailResponse.class)
@JsonSerialize(as = ImmutableErrorDetailResponse.class)
@Value.Immutable
@Value.Style(stagedBuilder = true)
public interface ErrorDetailResponse {

    String correlationId();

    List<ErrorDetailResponseItem> fault();

    default Optional<ErrorDetailResponseItem> primaryFault() {
        return Optional.ofNullable(fault()).stream().flatMap(Collection::stream).findFirst();
    }
}
