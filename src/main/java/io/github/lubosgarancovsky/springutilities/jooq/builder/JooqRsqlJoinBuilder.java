package io.github.lubosgarancovsky.springutilities.jooq.builder;



import cz.jirutka.rsql.parser.ast.ComparisonNode;
import cz.jirutka.rsql.parser.ast.LogicalNode;
import cz.jirutka.rsql.parser.ast.Node;
import io.github.lubosgarancovsky.springutilities.jooq.domain.ImmutableJoin;
import io.github.lubosgarancovsky.springutilities.jooq.domain.Join;
import io.github.lubosgarancovsky.springutilities.jooq.metadata.JooqColumnInfo;
import io.github.lubosgarancovsky.springutilities.jooq.metadata.JooqJoinableColumnInfo;
import org.jooq.Record;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


public class JooqRsqlJoinBuilder extends JooqRsqlWithListingMetadataBuilder<List<Join>> {
    public JooqRsqlJoinBuilder() {
    }

    public List<Join> build(LogicalNode logicalNode) {
        return (List)logicalNode.getChildren().stream().flatMap((c) -> {
            return ((List)this.build((Node)c)).stream();
        }).distinct().collect(Collectors.toList());
    }

    public List<Join> build(ComparisonNode comparisonNode) {
        String property = comparisonNode.getSelector();
        Optional<JooqColumnInfo<?>> maybe = this.getMetadata().findByName(property);
        if (maybe.isEmpty()) {
            throw new IllegalArgumentException("Unknown property: " + property);
        } else if (maybe.get() instanceof JooqJoinableColumnInfo) {
            JooqJoinableColumnInfo<Record, Object> columnInfo = (JooqJoinableColumnInfo)maybe.get();
            Join join = ImmutableJoin.builder().table(columnInfo.getTable()).type(columnInfo.getJoinType()).condition(columnInfo.getCondition()).build();
            return List.of(join);
        } else {
            return Collections.emptyList();
        }
    }
}
