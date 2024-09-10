package io.github.lubosgarancovsky.springutilities.port;

import io.github.lubosgarancovsky.springutilities.marker.Query;

public interface UseCaseQuery<Q extends Query, O> {

    O execute(Q query);
}
