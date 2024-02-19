package it.unicam.cs.pa.ma114110.area;

public abstract class SampleArea implements Area {
    private final String label;

    SampleArea(String label) {
        this.label = label;
    }

    @Override
    public String getLabel() {
        return this.label;
    }
}
