package io.github.lubosgarancovsky.springutilities.listing;

import java.util.List;

public interface PaginatedResult<T extends PaginatedResultItem> {
    List<T> items();

    Integer page();

    Integer pageSize();

    Integer totalCount();
}
