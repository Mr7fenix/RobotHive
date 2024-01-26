package it.unicam.cs.pa.ma114110.space;

import it.unicam.cs.pa.ma114110.space.CoordsInterface;

import java.util.Objects;

public class Coords implements CoordsInterface {
    private final double x;
    private final double y;

    /**
     * @param x the x coordinate for the specifico cell
     * @param y the y coordinate for the specifico cell
     */
    public Coords(double x, double y) {
        this.x = Objects.requireNonNull(x);
        this.y = Objects.requireNonNull(y);
    }


    @Override
    public double getX() {
        return x;
    }

    @Override
    public double getY() {
        return y;
    }
}
