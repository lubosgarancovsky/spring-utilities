package io.github.lubosgarancovsky.springutilities.jooq.builder;



import io.github.lubosgarancovsky.springutilities.jooq.metadata.JooqRsqlMetadata;

public abstract class JooqRsqlWithListingMetadataBuilder<T> implements JooqRsqlListingMetadataAwareBuilder<T> {

    private JooqRsqlMetadata metadata;

    public JooqRsqlWithListingMetadataBuilder() {
        
    }

    public void setJooqRsqlMetadata(JooqRsqlMetadata metadata) {
        this.metadata = metadata;
    }

    public JooqRsqlMetadata getMetadata() {
        return this.metadata;
    }
    
}
