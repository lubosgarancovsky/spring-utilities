package io.github.lubosgarancovsky.springutilities.jooq.metadata;

import org.jooq.Condition;
import org.jooq.JoinType;

public interface JooqRsqlMetadataOnStep {
    JooqRsqlMetadata joinOn(Condition var1);

    JooqRsqlMetadata joinOn(Condition var1, JoinType var2);
}
