package it.unicam.cs.pa.ma114110.area;

import it.unicam.cs.pa.ma114110.space.coords.SampleCoords;
import it.unicam.cs.pa.ma114110.space.coords.Coords;

public class CircularSampleArea extends SampleArea {
    private final double radius;
    private final SampleCoords center;

    public CircularSampleArea(String label, SampleCoords center, double radius) {
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

    public SampleCoords getCenter() {
        return center;
    }

    @Override
    public boolean contains(Coords coords) {
        double distance = Math.sqrt(Math.pow(coords.x() - center.x(), 2) + Math.pow(coords.y() - center.y(), 2));
        return distance <= radius;
    }
}
