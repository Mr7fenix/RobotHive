package it.unicam.cs.pa.ma114110.space;

public class Direction implements DirectionInterface {
    private final double x;
    private final double y;

    public Direction(double x, double y) {
        if (x > 1 || x < -1) {
            throw new IllegalArgumentException("x must be between -1 and 1");
        }

        if (y > 1 || y < -1) {
            throw new IllegalArgumentException("y must be between -1 and 1");
        }

        if (x == 0 && y == 0) {
            throw new IllegalArgumentException("one of x or y must be different of 0");
        }

        this.x = x;
        this.y = y;
    }

    @Override
    public double getX() {
        return this.x;
    }

    @Override
    public double getY() {
        return this.y;
    }
}


