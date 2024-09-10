package io.github.lubosgarancovsky.springutilities.listing;


import java.util.Collections;
import java.util.List;

public class ListOrderings<T extends ListingAttribute> {

    private final List<ListOrdering<T>> orderings;

    private ListOrderings(List<ListOrdering<T>> orderings) {
        this.orderings = orderings;
    }

    public static <T extends ListingAttribute> ListOrderings<T> empty() {
        return new ListOrderings<>(Collections.emptyList());
    }

    public static <T extends ListingAttribute> ListOrderings<T> create(
            List<ListOrdering<T>> orderings) {
        return new ListOrderings<>(orderings);
    }

    public List<ListOrdering<T>> asList() {
        return orderings;
    }
}
