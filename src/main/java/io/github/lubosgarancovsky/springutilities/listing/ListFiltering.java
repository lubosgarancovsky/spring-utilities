package io.github.lubosgarancovsky.springutilities.listing;

import cz.jirutka.rsql.parser.ast.Node;

import java.util.Optional;

public class ListFiltering<T extends ListingAttribute> {

    private final Optional<Node> option;

    private ListFiltering(Optional<Node> option) {
        this.option = option;
    }

    public static <U extends ListingAttribute> ListFiltering<U> none() {
        return new ListFiltering<>(Optional.empty());
    }

    public static <U extends ListingAttribute> ListFiltering<U> some(Node node) {
        return new ListFiltering<>(Optional.of(node));
    }

    public boolean isNone() {
        return option.isEmpty();
    }

    public Optional<Node> asOption() {
        return option;
    }

    public Node get() {
        return option.get();
    }
}