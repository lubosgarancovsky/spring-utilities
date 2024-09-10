package io.github.lubosgarancovsky.springutilities.rsql;


import cz.jirutka.rsql.parser.ast.Node;

public interface RsqlNodeParser<T> {

    T parse(Node node);
}
