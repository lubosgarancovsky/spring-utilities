package io.github.lubosgarancovsky.springutilities.listing;

import java.util.List;
import java.util.Optional;

import org.immutables.value.Value;

import cz.jirutka.rsql.parser.ast.Node;

public interface ListingQuery<T extends ListingAttribute> {

    @Value.Default
    default Integer page() {
        return 1;
    }

    Optional<Node> rsqlQuery();

    List<ListOrdering<T>> orderings();

    PageSize pageSize();

    @Value.Derived
    default int limit() {
        return pageSize().value();
    }

    @Value.Derived
    default int offset() {
        return Math.max(page() - 1, 0) * pageSize().value();
    }
}
