package io.github.lubosgarancovsky.springutilities.jooq.handler;


import io.github.lubosgarancovsky.springutilities.listing.ListingQuery;
import io.github.lubosgarancovsky.springutilities.listing.PaginatedResult;
import io.github.lubosgarancovsky.springutilities.jooq.handler.mapper.PaginatedResultMapper;
import io.github.lubosgarancovsky.springutilities.jooq.handler.mapper.TriFunction;
import io.github.lubosgarancovsky.springutilities.jooq.metadata.JooqRsqlMetadataConfigProvider;
import org.jooq.Record;
import org.jooq.SelectQuery;
import org.jooq.impl.DSL;

import java.util.function.Function;
import java.util.stream.Stream;

public class JooqPaginatedSelectQueryHandler<Q extends ListingQuery<?>, R extends PaginatedResult> implements JooqSelectQueryHandler<SelectQuery, PaginatedResultMapper<Q, R>> {
    private final Q query;
    private final JooqOneParamSelectQueryHandler<SelectQuery> rsqlQueryHandler;
    private final JooqOneParamSelectQueryHandler<SelectQuery> orderingQueryHandler;
    private final JooqOneParamSelectQueryHandler<SelectQuery> translationQueryHandler;

    public JooqPaginatedSelectQueryHandler(Q query, JooqRsqlMetadataConfigProvider jooqListingMetadataConfigProvider) {
        this.query = query;
        this.rsqlQueryHandler = new JooqRsqlSelectQueryHandler(query.rsqlQuery(), jooqListingMetadataConfigProvider);
        this.orderingQueryHandler = new JooqOrderingSelectQueryHandler(query.orderings(), jooqListingMetadataConfigProvider);
        this.translationQueryHandler = null;
    }

    public PaginatedResultMapper<Q, R> handle(SelectQuery dataQuery) {
        SelectQuery dataQ = this.rsqlQueryHandler.handle(dataQuery);
        final Integer totalCount = DSL.using(dataQ.configuration()).fetchCount(dataQ);
        SelectQuery orderedQuery = this.orderingQueryHandler.handle(dataQ);
        final SelectQuery groupedByQuery = this.translationQueryHandler != null ? this.translationQueryHandler.handle(orderedQuery) : orderedQuery;
        groupedByQuery.addLimit(this.query.limit());
        groupedByQuery.addOffset(this.query.offset());
        return new PaginatedResultMapper<Q, R>() {
            public R map(TriFunction<Stream<Record>, Integer, Q, R> fn) {
                Stream<Record> orderedStream = groupedByQuery.fetchStream();

                PaginatedResult var3;
                try {
                    var3 = this.map((selectQuery) -> {
                        return orderedStream;
                    }, fn);
                } catch (Throwable var6) {
                    if (orderedStream != null) {
                        try {
                            orderedStream.close();
                        } catch (Throwable var5) {
                            var6.addSuppressed(var5);
                        }
                    }

                    throw var6;
                }

                if (orderedStream != null) {
                    orderedStream.close();
                }

                return (R) var3;
            }

            public <RECORDS> R map(Function<SelectQuery, RECORDS> records, TriFunction<RECORDS, Integer, Q, R> mapper) {
                return mapper.apply(records.apply(groupedByQuery), totalCount, JooqPaginatedSelectQueryHandler.this.query);
            }
        };
    }
}