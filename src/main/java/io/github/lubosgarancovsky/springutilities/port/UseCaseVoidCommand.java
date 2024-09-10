package io.github.lubosgarancovsky.springutilities.port;

import io.github.lubosgarancovsky.springutilities.marker.Command;

public interface UseCaseVoidCommand<C extends Command> {

    void execute(C command);
}
