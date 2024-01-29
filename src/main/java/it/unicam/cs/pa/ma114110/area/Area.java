package it.unicam.cs.pa.ma114110.area;

import it.unicam.cs.pa.ma114110.space.Coords;

public abstract class Area implements AreaInterface {
    private final String label;

    Area(String label) {
        this.label = label;
    }

    @Override
    public String getLabel() {
        return this.label;
    }
}
