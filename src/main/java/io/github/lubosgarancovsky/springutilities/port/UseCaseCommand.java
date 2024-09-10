package io.github.lubosgarancovsky.springutilities.port;

import io.github.lubosgarancovsky.springutilities.marker.Command;

public interface UseCaseCommand<C extends Command, O> {

    O execute(C command);
}
