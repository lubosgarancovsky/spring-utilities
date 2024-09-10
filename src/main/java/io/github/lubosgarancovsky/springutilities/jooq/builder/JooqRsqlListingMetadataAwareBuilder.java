package io.github.lubosgarancovsky.springutilities.jooq.builder;


import io.github.lubosgarancovsky.springutilities.jooq.metadata.JooqRsqlMetadataAware;
import io.github.lubosgarancovsky.springutilities.rsql.RsqlNodeBuilder;

public interface JooqRsqlListingMetadataAwareBuilder<T> extends RsqlNodeBuilder<T>, JooqRsqlMetadataAware {
    
}
