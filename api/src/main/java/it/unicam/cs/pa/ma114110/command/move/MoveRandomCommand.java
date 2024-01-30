package it.unicam.cs.pa.ma114110.command.move;

import it.unicam.cs.pa.ma114110.command.SampleCommand;
import it.unicam.cs.pa.ma114110.space.Coords;

public class MoveRandomCommand extends SampleCommand {
    private final Coords firstPoint;
    private final Coords secondPoint;

    private final double s;


    public MoveRandomCommand(Coords firstPoint, Coords secondPointPoint, double s) {
        super();

        if (firstPoint == null) {
            throw new IllegalArgumentException("First point cannot be null");
        }

        if (secondPointPoint == null) {
            throw new IllegalArgumentException("Second point cannot be null");
        }

        if (s <= 0) {
            throw new IllegalArgumentException("s must be greater than 0");
        }

        this.firstPoint = firstPoint;
        this.secondPoint = secondPointPoint;
        this.s = s;
    }

    public Coords getFirstPoint() {
        return firstPoint;
    }

    public Coords getSecondPoint() {
        return secondPoint;
    }

    public double getS() {
        return s;
    }
}
