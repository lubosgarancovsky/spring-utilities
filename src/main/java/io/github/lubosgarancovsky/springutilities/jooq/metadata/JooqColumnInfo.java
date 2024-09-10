package io.github.lubosgarancovsky.springutilities.jooq.metadata;

import org.jooq.Field;

import io.github.lubosgarancovsky.springutilities.converter.FieldValueConverter;

public class JooqColumnInfo<T> {
    
    private Field<T> field;

    private FieldValueConverter<T> converter;

    public JooqColumnInfo(){
    }

    public Field<T> getField() { return this.field; }

    public void setField(Field<T> field) { this.field = field; }

    public FieldValueConverter<T> getConverter() { return this.converter; }

    public void setConverter(FieldValueConverter<T> converter) { this.converter = converter; }
    
}
