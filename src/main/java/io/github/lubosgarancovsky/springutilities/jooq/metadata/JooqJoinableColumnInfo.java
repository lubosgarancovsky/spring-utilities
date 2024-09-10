package io.github.lubosgarancovsky.springutilities.jooq.metadata;

import org.jooq.Condition;
import org.jooq.JoinType;
import org.jooq.Record;
import org.jooq.Table;

public class JooqJoinableColumnInfo<R extends Record, T> extends JooqColumnInfo<T> {
    
    private Table<R> table;

    private Condition condition;

    private JoinType joinType;

    public JooqJoinableColumnInfo() {}

    public Table<R> getTable() {
        return table;
    }

    public void setTable(Table<R> table) {
        this.table = table;
    }

    public Condition getCondition() {
        return condition;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }

    public JoinType getJoinType() {
        return joinType;
    }

    public void setJoinType(JoinType joinType) {
        this.joinType = joinType;
    }
}
