package io.github.lubosgarancovsky.springutilities.jooq.metadata;

import java.util.Optional;

import org.jooq.Field;
import org.jooq.Record;
import org.jooq.TableField;

import io.github.lubosgarancovsky.springutilities.converter.FieldValueConverter;

public interface JooqRsqlMetadata {

    <T> JooqRsqlMetadata addColumnInfo(String var1, Field<T> var2);

    <T> JooqRsqlMetadata addColumnInfo(String var1, Field<T> var2, FieldValueConverter<T> var3);

    <T> JooqRsqlMetadata addColumnInfo(String var1, JooqColumnInfo<T> var2);

    <R extends Record, T> JooqRsqlMetadataOnStep addJoinableColumnInfo(String var1, TableField<R, T> var2);

    <R extends Record, T> JooqRsqlMetadataOnStep addJoinableColumnInfo(String var1, TableField<R, T> var2, FieldValueConverter<T> var3);

    <R extends Record, T> JooqRsqlMetadataOnStep addJoinableColumnInfo(String var1, JooqJoinableColumnInfo<R, T> var2);

    JooqRsqlMetadata registerConverter(FieldValueConverter var1);

    Optional<JooqColumnInfo<?>> findByName(String var1);



    
}
