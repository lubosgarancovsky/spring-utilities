package io.github.lubosgarancovsky.springutilities.jooq.transactional;


import org.jooq.DSLContext;

public interface PersistenceContext {
    DSLContext get();
}
