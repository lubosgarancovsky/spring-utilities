package io.github.lubosgarancovsky.springutilities.listing;

import java.util.Set;

import cz.jirutka.rsql.parser.ast.ComparisonOperator;
import cz.jirutka.rsql.parser.ast.RSQLOperators;

public class ListFilteringOperators {

    public static final ComparisonOperator LIKE = new ComparisonOperator("=like=", true);

    public static Set<ComparisonOperator> all() {
        Set<ComparisonOperator> operators = RSQLOperators.defaultOperators();
        operators.add(ListFilteringOperators.LIKE);
        return operators;
    }
}
