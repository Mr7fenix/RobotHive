package it.unicam.cs.pa.ma114110.command.move;

import it.unicam.cs.pa.ma114110.command.Command;
import it.unicam.cs.pa.ma114110.space.coords.Coords;

public record MoveRandomCommand(Coords firstPoint, Coords secondPoint, double s) implements Command {
    public MoveRandomCommand {

        if (firstPoint == null) {
            throw new IllegalArgumentException("First point cannot be null");
        }

        if (secondPoint == null) {
            throw new IllegalArgumentException("Second point cannot be null");
        }

        if (s <= 0) {
            throw new IllegalArgumentException("s must be greater than 0");
        }
    }
}
