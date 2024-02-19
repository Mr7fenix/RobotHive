package it.unicam.cs.pa.ma114110.space;

import it.unicam.cs.pa.ma114110.space.coords.Coords;
import it.unicam.cs.pa.ma114110.space.coords.SampleCoords;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SampleCoordsTest {

    @Test
    void getX() {
        Coords coords = new SampleCoords(1, 2);
        assertEquals(1, coords.x());
    }

    @Test
    void getY() {
        Coords coords = new SampleCoords(1, 2);
        assertEquals(2, coords.y());
    }
}