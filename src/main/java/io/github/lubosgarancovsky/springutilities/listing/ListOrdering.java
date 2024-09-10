package io.github.lubosgarancovsky.springutilities.listing;

import org.immutables.value.Value;

@Value.Immutable
public interface ListOrdering<T extends ListingAttribute> {

    @Value.Parameter
    T attribute();

    @Value.Parameter
    boolean ascending();
}
