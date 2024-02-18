package it.unicam.cs.pa.ma114110.area;

import it.unicam.cs.pa.ma114110.space.Coords;
import it.unicam.cs.pa.ma114110.space.CoordsInterface;

public class RectangleArea extends Area {
    private final Coords start;
    private final double height;
    private final double width;

    public RectangleArea(String label, Coords start, double height, double width) {
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
    public boolean contains(CoordsInterface coords) {
        if (coords == null) {
            throw new NullPointerException("Coords cannot be null");
        }

        return coords.x() >= start.x() && coords.x() <= start.x() + width
                && coords.y() >= start.y() && coords.y() <= start.y() + height;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }
}
