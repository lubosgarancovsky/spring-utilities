package io.github.lubosgarancovsky.springutilities.jooq.builder;

import cz.jirutka.rsql.parser.ast.ComparisonNode;
import cz.jirutka.rsql.parser.ast.ComparisonOperator;
import cz.jirutka.rsql.parser.ast.LogicalNode;
import cz.jirutka.rsql.parser.ast.RSQLOperators;
import io.github.lubosgarancovsky.springutilities.listing.ListFilteringOperators;
import io.github.lubosgarancovsky.springutilities.jooq.metadata.JooqColumnInfo;
import io.github.lubosgarancovsky.springutilities.rsql.RsqlErrorCode;
import org.jooq.Condition;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class JooqRsqlConditionBuilder extends JooqRsqlWithListingMetadataBuilder<Condition> {
    public static final String NULL_VALUE = "null";

    public JooqRsqlConditionBuilder() {
    }

    public Condition build(LogicalNode logicalNode) {
        List<Condition> childConditions = (List)logicalNode.getChildren().stream().map(this::build).collect(Collectors.toList());
        switch (logicalNode.getOperator()) {
            case AND:
                return (Condition)childConditions.stream().reduce(Condition::and).get();
            case OR:
                return (Condition)childConditions.stream().reduce(Condition::or).get();
            default:
                throw new IllegalArgumentException("Unknown logical operator " + String.valueOf(logicalNode.getOperator()));
        }
    }

    public Condition build(ComparisonNode comparisonNode) {
        String property = comparisonNode.getSelector();
        ComparisonOperator operator = comparisonNode.getOperator();
        List<String> arguments = comparisonNode.getArguments();
        Optional<JooqColumnInfo<?>> maybe = this.getMetadata().findByName(property);
        if (maybe.isEmpty()) {
            throw new IllegalArgumentException("Unknown property: " + property);
        } else {
            JooqColumnInfo<Object> columnInfo = (JooqColumnInfo)maybe.get();
            if (operator.equals(RSQLOperators.EQUAL)) {
                return this.buildEqualCondition(columnInfo, (String)arguments.get(0));
            } else if (operator.equals(RSQLOperators.NOT_EQUAL)) {
                return this.buildNotEqualCondition(columnInfo, (String)arguments.get(0));
            } else if (operator.equals(RSQLOperators.GREATER_THAN)) {
                return columnInfo.getField().greaterThan(columnInfo.getConverter().from((String)arguments.get(0)));
            } else if (operator.equals(RSQLOperators.GREATER_THAN_OR_EQUAL)) {
                return columnInfo.getField().greaterOrEqual(columnInfo.getConverter().from((String)arguments.get(0)));
            } else if (operator.equals(RSQLOperators.LESS_THAN)) {
                return columnInfo.getField().lessThan(columnInfo.getConverter().from((String)arguments.get(0)));
            } else if (operator.equals(RSQLOperators.LESS_THAN_OR_EQUAL)) {
                return columnInfo.getField().lessOrEqual(columnInfo.getConverter().from((String)arguments.get(0)));
            } else if (operator.equals(RSQLOperators.IN)) {
                return columnInfo.getField().in((Collection)arguments.stream().map((a) -> {
                    return columnInfo.getConverter().from(a);
                }).collect(Collectors.toList()));
            } else if (operator.equals(RSQLOperators.NOT_IN)) {
                return columnInfo.getField().notIn((Collection)arguments.stream().map((a) -> {
                    return columnInfo.getConverter().from(a);
                }).collect(Collectors.toList()));
            } else if (operator.equals(ListFilteringOperators.LIKE)) {
                if (!columnInfo.getConverter().isAccessibleFor(String.class)) {
                    throw RsqlErrorCode.INVALID_RSQL_OPERATOR.createError(new Object[]{operator, property}).convertToException();
                } else {
                    return columnInfo.getField().likeIgnoreCase(((String)arguments.get(0)).replaceFirst("^\\s*(\\*)?\\s*(.*?)\\s*(\\*)?\\s*$", "$1$2$3").replace('*', '%'));
                }
            } else {
                throw new IllegalArgumentException("Unknown operator: " + String.valueOf(operator));
            }
        }
    }

    private Condition buildEqualCondition(JooqColumnInfo<Object> fieldInfo, String argument) {
        return "null".equals(argument) ? fieldInfo.getField().isNull() : fieldInfo.getField().eq(fieldInfo.getConverter().from(argument));
    }

    private Condition buildNotEqualCondition(JooqColumnInfo<Object> fieldInfo, String argument) {
        return "null".equals(argument) ? fieldInfo.getField().isNotNull() : fieldInfo.getField().notEqual(fieldInfo.getConverter().from(argument));
    }
}