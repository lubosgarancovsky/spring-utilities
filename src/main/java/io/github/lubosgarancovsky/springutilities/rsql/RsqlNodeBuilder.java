package io.github.lubosgarancovsky.springutilities.rsql;


import cz.jirutka.rsql.parser.ast.ComparisonNode;
import cz.jirutka.rsql.parser.ast.LogicalNode;
import cz.jirutka.rsql.parser.ast.Node;

public interface RsqlNodeBuilder<T> {

    T build(LogicalNode node);

    T build(ComparisonNode node);

    default T build(Node node) {
        if (node instanceof LogicalNode) {
            return build((LogicalNode) node);
        } else if (node instanceof ComparisonNode) {
            return build((ComparisonNode) node);
        } else {
            throw new IllegalArgumentException("Unknown node type: " + node.getClass());
        }
    }
}
