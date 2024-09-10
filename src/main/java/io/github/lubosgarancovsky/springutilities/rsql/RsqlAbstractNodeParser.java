package io.github.lubosgarancovsky.springutilities.rsql;

import cz.jirutka.rsql.parser.ast.*;

public class RsqlAbstractNodeParser<T> extends NoArgRSQLVisitorAdapter<T>
        implements RsqlNodeParser<T> {

    private final RsqlNodeBuilder<T> rsqlBuilder;

    public RsqlAbstractNodeParser(RsqlNodeBuilder<T> rsqlBuilder) {
        this.rsqlBuilder = rsqlBuilder;
    }

    @Override
    public T parse(Node node) {
        return rsqlBuilder.build(node);
    }

    @Override
    public T visit(AndNode node) {
        return parse(node);
    }

    @Override
    public T visit(OrNode node) {
        return parse(node);
    }

    @Override
    public T visit(ComparisonNode node) {
        return parse(node);
    }
}
