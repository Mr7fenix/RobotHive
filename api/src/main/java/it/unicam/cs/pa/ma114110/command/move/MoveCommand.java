package it.unicam.cs.pa.ma114110.command.move;

import it.unicam.cs.pa.ma114110.command.SampleCommand;
import it.unicam.cs.pa.ma114110.space.Direction;

public class MoveCommand extends SampleCommand {
    private final Direction direction;
    private final double s;


    public MoveCommand(Direction direction, double s) {
        super();

        if (direction == null) {
            throw new IllegalArgumentException("Direction cannot be null");
        }

        if (s <= 0) {
            throw new IllegalArgumentException("s must be greater than 0");
        }

        this.direction = direction;
        this.s = s;
    }

    public Direction getDirection() {
        return this.direction;
    }

    public double getS() {
        return this.s;
    }
}
