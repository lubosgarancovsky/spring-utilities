package io.github.lubosgarancovsky.springutilities.dto;

import java.util.List;

public interface ListingResponse<T> {

    List<T> items();

    Integer totalCount();

    Integer page();

    Integer pageSize();
}
