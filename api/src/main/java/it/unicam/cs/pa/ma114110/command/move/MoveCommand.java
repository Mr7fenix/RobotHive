package it.unicam.cs.pa.ma114110.command.move;

import it.unicam.cs.pa.ma114110.command.Command;
import it.unicam.cs.pa.ma114110.space.direction.SampleDirection;

public record MoveCommand(SampleDirection direction, double s) implements Command {
    public MoveCommand {

        if (direction == null) {
            throw new IllegalArgumentException("Direction cannot be null");
        }

        if (s <= 0) {
            throw new IllegalArgumentException("s must be greater than 0");
        }

    }
}
