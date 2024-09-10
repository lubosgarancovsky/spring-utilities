package io.github.lubosgarancovsky.springutilities.jooq.metadata;

import io.github.lubosgarancovsky.springutilities.jooq.builder.JooqRsqlListingMetadataAwareBuilder;
import io.github.lubosgarancovsky.springutilities.rsql.RsqlAbstractNodeParser;


public class JooqRsqlWithMetadataParser<T> extends RsqlAbstractNodeParser<T> {
    
    private JooqRsqlMetadataConfigProvider jooqListingMetadataConfigProvider;

    public JooqRsqlWithMetadataParser(JooqRsqlListingMetadataAwareBuilder<T> jooqRsqlBuilder, JooqRsqlMetadataConfigProvider jooqListingMetadataConfigProvider) {
        super(jooqRsqlBuilder);
        this.jooqListingMetadataConfigProvider = jooqListingMetadataConfigProvider;
        this.configureJooqRsqlMetadataAwareBuilder(jooqRsqlBuilder);
    }

    private void configure(JooqRsqlMetadata metadata) {
        //metadata.registerConverter(new BooleanValueConverter()).registerConverter(new DateTimeValueConverter()).registerConverter(new UUIDValueConverter()).registerConverter(new LocalDateValueConverter());
        this.jooqListingMetadataConfigProvider.configure(metadata);
    }

    private void configureJooqRsqlMetadataAwareBuilder(JooqRsqlMetadataAware jooqListingMetadataAware) {
        JooqRsqlMetadata columnInfoMetadata = new JooqRsqlMetadataImpl();
        this.configure(columnInfoMetadata);
        jooqListingMetadataAware.setJooqRsqlMetadata(columnInfoMetadata);
    }
}
