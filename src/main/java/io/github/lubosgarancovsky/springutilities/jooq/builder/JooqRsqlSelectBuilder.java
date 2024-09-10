package io.github.lubosgarancovsky.springutilities.jooq.builder;


import cz.jirutka.rsql.parser.ast.ComparisonNode;
import cz.jirutka.rsql.parser.ast.LogicalNode;
import cz.jirutka.rsql.parser.ast.Node;
import io.github.lubosgarancovsky.springutilities.jooq.domain.Join;
import io.github.lubosgarancovsky.springutilities.jooq.metadata.JooqRsqlMetadata;
import org.jooq.Condition;
import org.jooq.SelectQuery;
import org.jooq.impl.QOM;

import java.util.Iterator;
import java.util.List;

public class JooqRsqlSelectBuilder extends JooqRsqlWithListingMetadataBuilder<SelectQuery> {
    private SelectQuery query;
    
    private JooqRsqlConditionBuilder jooqRsqlConditionBuilder;

    private JooqRsqlJoinBuilder jooqRsqlJoinBuilder;

    private String AGGREGATE_FUNCTION_CLASS_NAME = "AbstractAggregateFunction";

    public JooqRsqlSelectBuilder(SelectQuery query) {
        this.query = query;
        this.jooqRsqlConditionBuilder = new JooqRsqlConditionBuilder();
        this.jooqRsqlJoinBuilder = new JooqRsqlJoinBuilder();
    }

    public SelectQuery build(LogicalNode node) {
        this.build(this.query, node);
        return this.query;
    }

    public SelectQuery build(ComparisonNode node) {
        this.build(this.query, node);
        return this.query;
    }

    private void build(SelectQuery query, Node node) {
        this.addJoin(query, node);
        if (this.nodeIsAggregateFunction(query, node)) {
            this.addHavingCondition(query, node);
        } else {
            this.addWhereCondition(query, node);
        }

    }

    private void addJoin(SelectQuery query, Node node) {
        List<Join> joins = (List) this.jooqRsqlJoinBuilder.build(node);
        Iterator var4 = joins.iterator();

        while(var4.hasNext()) {
            Join join = (Join)var4.next();
            query.addJoin(join.table(), join.type(), join.condition());
        }

    }

    private void addWhereCondition(SelectQuery query, Node node) {
        query.addConditions((Condition)this.jooqRsqlConditionBuilder.build(node));
    }

    private void addHavingCondition(SelectQuery query, Node node) {
        query.addHaving((Condition)this.jooqRsqlConditionBuilder.build(node));
    }

    public void setJooqRsqlMetadata(JooqRsqlMetadata metadata) {
        super.setJooqRsqlMetadata(metadata);
        this.jooqRsqlConditionBuilder.setJooqRsqlMetadata(metadata);
        this.jooqRsqlJoinBuilder.setJooqRsqlMetadata(metadata);
    }

    private boolean nodeIsAggregateFunction(SelectQuery query, Node node) {
        Object selectField = this.getSelectField(query, node);

        try {
            if (selectField instanceof QOM.FieldAlias<?> aliasField) {
                return this.AGGREGATE_FUNCTION_CLASS_NAME.equals(aliasField.$aliased().getClass().getSuperclass().getSimpleName());
            } else {
                return this.AGGREGATE_FUNCTION_CLASS_NAME.equals(selectField.getClass().getSuperclass().getSimpleName());
            }
        } catch (Exception var5) {
            return false;
        }
    }

    private Object getSelectField(SelectQuery query, Node node) {
        if (node instanceof ComparisonNode comparisonNode) {
            int indexOfNodeInSelectFieldList = query.indexOf(comparisonNode.getSelector());
            if (indexOfNodeInSelectFieldList >= 0) {
                return query.getSelect().get(indexOfNodeInSelectFieldList);
            }
        }

        return null;
    }
}
