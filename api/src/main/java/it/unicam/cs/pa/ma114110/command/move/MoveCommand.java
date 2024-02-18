package it.unicam.cs.pa.ma114110.command.move;

import it.unicam.cs.pa.ma114110.command.CommandInterface;
import it.unicam.cs.pa.ma114110.space.Direction;

public record MoveCommand(Direction direction, double s) implements CommandInterface {
    public MoveCommand {

        if (direction == null) {
            throw new IllegalArgumentException("Direction cannot be null");
        }

        if (s <= 0) {
            throw new IllegalArgumentException("s must be greater than 0");
        }

    }
}
