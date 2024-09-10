package io.github.lubosgarancovsky.springutilities.jooq.handler.mapper;


import java.util.function.Function;
import java.util.stream.Stream;

import org.jooq.Record;
import org.jooq.SelectQuery;

import io.github.lubosgarancovsky.springutilities.listing.ListingQuery;
import io.github.lubosgarancovsky.springutilities.listing.PaginatedResult;

public interface PaginatedResultMapper<LISTING extends ListingQuery, RESULT extends PaginatedResult> {
    
    RESULT map(TriFunction<Stream<Record>, Integer, LISTING, RESULT> var1);

    <RECORDS> RESULT map(Function<SelectQuery, RECORDS> var1, TriFunction<RECORDS, Integer, LISTING, RESULT> var2);

}
