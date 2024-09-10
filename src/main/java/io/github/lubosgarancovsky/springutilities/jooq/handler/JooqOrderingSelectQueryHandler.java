package io.github.lubosgarancovsky.springutilities.jooq.handler;

import io.github.lubosgarancovsky.springutilities.listing.ListOrdering;
import io.github.lubosgarancovsky.springutilities.listing.ListingAttribute;
import io.github.lubosgarancovsky.springutilities.jooq.domain.ImmutableJoin;
import io.github.lubosgarancovsky.springutilities.jooq.domain.Join;
import io.github.lubosgarancovsky.springutilities.jooq.metadata.*;
import org.jooq.OrderField;
import org.jooq.Record;
import org.jooq.SelectQuery;
import org.jooq.impl.DSL;

import java.util.List;
import java.util.Optional;


public class JooqOrderingSelectQueryHandler<T extends ListingAttribute> implements JooqOneParamSelectQueryHandler<SelectQuery> {
    private final List<ListOrdering<T>> orderings;
    private final JooqListingMetadataConfigHolder metadataConfigHolder;

    public JooqOrderingSelectQueryHandler(List<ListOrdering<T>> orderings, JooqRsqlMetadataConfigProvider rsqlParserConfigProvider) {
        this.orderings = orderings;
        this.metadataConfigHolder = new JooqListingMetadataConfigHolder(rsqlParserConfigProvider);
    }

    public SelectQuery handle(SelectQuery query) {
        if (!this.orderings.isEmpty()) {
            this.orderings.forEach((ordering) -> {
                this.addOrderBy(ordering, query);
                this.addJoin(ordering, query);
            });
        }

        return query;
    }

    private void addOrderBy(ListOrdering<T> ordering, SelectQuery query) {
        JooqColumnInfo<Object> columnInfo = (JooqColumnInfo<Object>) this.getJooqColumnInfo(ordering);
        if (columnInfo.getField().getType().equals(String.class)) {
            JooqColumnInfo<String> stringColumnInfo = (JooqColumnInfo<String>) this.getJooqColumnInfo(ordering);
            query.addOrderBy(new OrderField[]{ordering.ascending() ? DSL.upper(stringColumnInfo.getField()).asc().nullsLast() : DSL.upper(stringColumnInfo.getField()).desc().nullsFirst()});
        } else {
            query.addOrderBy(new OrderField[]{ordering.ascending() ? columnInfo.getField().asc().nullsLast() : columnInfo.getField().desc().nullsFirst()});
        }

    }

    // private void addTranslatedOrderBy(ListOrdering<T> ordering, SelectQuery query) {
    //     JooqColumnInfo<Object> columnInfo = (JooqColumnInfo<Object>) this.getJooqColumnInfo(ordering);
    //     if (columnInfo.getField().getType().equals(String.class)) {
    //         JooqColumnInfo<String> stringColumnInfo = (JooqColumnInfo<String>) this.getJooqColumnInfo(ordering);
    //         Field<?> collation = MonolingualCollationContext.getCollationTemplate(query.configuration().dialect(), DSL.upper(stringColumnInfo.getField()), ordering.attribute(), this.languageCodes);
    //         query.addOrderBy(new OrderField[]{ordering.ascending() ? collation.asc().nullsLast() : collation.desc().nullsFirst()});
    //     } else {
    //         Field<?> collation = MonolingualCollationContext.getCollationTemplate(query.configuration().dialect(), columnInfo.getField(), ordering.attribute(), this.languageCodes);
    //         query.addOrderBy(new OrderField[]{ordering.ascending() ? collation.asc().nullsLast() : collation.desc().nullsFirst()});
    //     }

    // }

    private void addJoin(ListOrdering<T> ordering, SelectQuery query) {
        if (this.getJooqColumnInfo(ordering) instanceof JooqJoinableColumnInfo) {
            JooqJoinableColumnInfo<Record, Object> columnInfo = (JooqJoinableColumnInfo)this.getJooqColumnInfo(ordering);
            Join join = ImmutableJoin.builder().table(columnInfo.getTable()).type(columnInfo.getJoinType()).condition(columnInfo.getCondition()).build();
            query.addJoin(join.table(), join.type(), join.condition());
        }

    }

    private JooqColumnInfo<?> getJooqColumnInfo(ListOrdering<T> ordering) {
        Optional<JooqColumnInfo<?>> maybe = this.metadataConfigHolder.metadata.findByName(ordering.attribute().apiName());
        if (maybe.isEmpty()) {
            throw new IllegalArgumentException("Unknown property: " + ordering.attribute().apiName());
        } else {
            return maybe.get();
        }
    }

    static class JooqListingMetadataConfigHolder {
        private JooqRsqlMetadata metadata = new JooqRsqlMetadataImpl();

        private JooqListingMetadataConfigHolder(JooqRsqlMetadataConfigProvider provider) {
            provider.configure(this.metadata);
        }
    }
}