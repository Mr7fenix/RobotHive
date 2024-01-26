package it.unicam.cs.pa.ma114110.area;

import it.unicam.cs.pa.ma114110.space.Coords;

public class SquaredArea extends Area {
    private final Coords start;
    private final Coords end;

    public SquaredArea(String label, Coords start, Coords end) {
        super(label);

        if (start == null || end == null) {
            throw new IllegalArgumentException("Coords cannot be null");
        }

        this.start = start;
        this.end = end;
    }

    public Coords getStart() {
        return start;
    }

    public Coords getEnd() {
        return end;
    }
}
