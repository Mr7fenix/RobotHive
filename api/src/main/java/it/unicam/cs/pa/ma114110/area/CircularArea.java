package it.unicam.cs.pa.ma114110.area;

import it.unicam.cs.pa.ma114110.space.Coords;
import it.unicam.cs.pa.ma114110.space.CoordsInterface;

public class CircularArea extends Area {
    private final double radius;
    private final Coords center;

    public CircularArea(String label, Coords center, double radius) {
        super(label);

        if (center == null) {
            throw new IllegalArgumentException("Coords cannot be null");
        }

        if (radius <= 0) {
            throw new IllegalArgumentException("Radius must be positive");
        }

        this.center = center;
        this.radius = radius;
    }

    public double getRadius() {
        return radius;
    }

    public Coords getCenter() {
        return center;
    }

    @Override
    public boolean contains(CoordsInterface coords) {
        double distance = Math.sqrt(Math.pow(coords.x() - center.x(), 2) + Math.pow(coords.y() - center.y(), 2));
        return distance <= radius;
    }
}
