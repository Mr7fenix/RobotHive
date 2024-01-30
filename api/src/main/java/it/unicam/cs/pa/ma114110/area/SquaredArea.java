package it.unicam.cs.pa.ma114110.area;

import it.unicam.cs.pa.ma114110.space.Coords;

public class SquaredArea extends Area {
    private final Coords start;
    private final double height;
    private final double width;

    public SquaredArea(String label, Coords start, double height, double width) {
        super(label);

        if (start == null) {
            throw new IllegalArgumentException("Coords cannot be null");
        }

        if (height <= 0) {
            throw new IllegalArgumentException("Height must be positive");
        }

        if (width <= 0) {
            throw new IllegalArgumentException("Width must be positive");
        }

        this.start = start;
        this.height = height;
        this.width = width;
    }

    public Coords getStart() {
        return start;
    }

    @Override
    public boolean contains(Coords coords) {
        if (coords == null) {
            throw new NullPointerException("Coords cannot be null");
        }

        return coords.getX() >= start.getX() && coords.getX() <= start.getX() + width
                && coords.getY() >= start.getY() && coords.getY() <= start.getY() + height;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }
}
