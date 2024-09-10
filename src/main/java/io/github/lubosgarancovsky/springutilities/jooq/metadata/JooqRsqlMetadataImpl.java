package io.github.lubosgarancovsky.springutilities.jooq.metadata;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.jooq.Condition;
import org.jooq.Field;
import org.jooq.JoinType;
import org.jooq.Record;
import org.jooq.TableField;

import io.github.lubosgarancovsky.springutilities.converter.EmptyValueConverter;
import io.github.lubosgarancovsky.springutilities.converter.FieldValueConverter;


public final class JooqRsqlMetadataImpl implements JooqRsqlMetadata, JooqRsqlMetadataOnStep {
    private Map<String, JooqColumnInfo<?>> attributes = new LinkedHashMap();
    private List<FieldValueConverter> converters = new ArrayList();

    public JooqRsqlMetadataImpl() {
    }

    public <T> JooqRsqlMetadataImpl addColumnInfo(String name, Field<T> field) {
        this.addColumnInfo(name, field, this.findConverter(field));
        return this;
    }

    public <T> JooqRsqlMetadataImpl addColumnInfo(String name, Field<T> field, FieldValueConverter<T> converter) {
        JooqColumnInfo<T> columnInfo = new JooqColumnInfo();
        columnInfo.setField(field);
        columnInfo.setConverter(converter);
        this.addColumnInfo(name, columnInfo);
        return this;
    }

    public JooqRsqlMetadataImpl addColumnInfo(String name, JooqColumnInfo info) {
        this.attributes.put(name, info);
        return this;
    }

    public <R extends Record, T> JooqRsqlMetadataImpl addJoinableColumnInfo(String name, TableField<R, T> field) {
        this.addJoinableColumnInfo(name, field, this.findConverter(field));
        return this;
    }

    public <R extends Record, T> JooqRsqlMetadataImpl addJoinableColumnInfo(String name, TableField<R, T> field, FieldValueConverter<T> converter) {
        JooqJoinableColumnInfo<R, T> columnInfo = new JooqJoinableColumnInfo();
        columnInfo.setField(field);
        columnInfo.setTable(field.getTable());
        columnInfo.setConverter(converter);
        this.addJoinableColumnInfo(name, columnInfo);
        return this;
    }

    public <R extends Record, T> JooqRsqlMetadataImpl addJoinableColumnInfo(String name, JooqJoinableColumnInfo<R, T> info) {
        this.attributes.put(name, info);
        return this;
    }

    public JooqRsqlMetadataImpl joinOn(Condition condition, JoinType type) {
        JooqColumnInfo columnInfo = this.findLastAttribute();
        if (columnInfo instanceof JooqJoinableColumnInfo) {
            ((JooqJoinableColumnInfo)columnInfo).setCondition(condition);
            ((JooqJoinableColumnInfo)columnInfo).setJoinType(type);
            return this;
        } else {
            throw new IllegalStateException("Join condition can be applied only for JooqJoinableColumnInfo.");
        }
    }

    public JooqRsqlMetadata joinOn(Condition condition) {
        this.joinOn(condition, JoinType.JOIN);
        return this;
    }

    public Optional<JooqColumnInfo<?>> findByName(String selector) {
        return Optional.ofNullable((JooqColumnInfo)this.attributes.get(selector));
    }

    public JooqRsqlMetadataImpl registerConverter(FieldValueConverter converter) {
        this.converters.add(converter);
        return this;
    }

    private <T> FieldValueConverter<T> findConverter(Field<T> field) {
        return (FieldValueConverter)this.converters.stream().filter((converter) -> {
            return converter.isAccessibleFor(field.getType());
        }).findFirst().orElse(new EmptyValueConverter());
    }

    private JooqColumnInfo<?> findLastAttribute() {
        Iterator<Map.Entry<String, JooqColumnInfo<?>>> iter = this.attributes.entrySet().iterator();

        Map.Entry entry;
        for(entry = null; iter.hasNext(); entry = (Map.Entry)iter.next()) {
        }

        return (JooqColumnInfo)entry.getValue();
    }
}