package io.github.lubosgarancovsky.springutilities.jooq.handler;


public interface JooqSelectQueryHandler<T, R> {
    R handle(T var1);
}
