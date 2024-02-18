package it.unicam.cs.pa.ma114110.command.move;

import it.unicam.cs.pa.ma114110.command.CommandInterface;
import it.unicam.cs.pa.ma114110.space.CoordsInterface;

public record MoveRandomCommand(CoordsInterface firstPoint, CoordsInterface secondPoint, double s) implements CommandInterface {
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
