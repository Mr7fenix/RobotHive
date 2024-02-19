package it.unicam.cs.pa.ma114110.area;

import it.unicam.cs.pa.ma114110.space.coords.SampleCoords;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SampleAreaTest {

    @Test
    void getLabel() {
        SampleArea sampleArea = new RectangleSampleArea("Ciao", new SampleCoords(0, 0), 1, 1);
        assertEquals("Ciao", sampleArea.getLabel());
    }
}