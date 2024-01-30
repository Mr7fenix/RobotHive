package it.unicam.cs.pa.ma114110.area;

import it.unicam.cs.pa.ma114110.space.Coords;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AreaTest {

    @Test
    void getLabel() {
        Area area = new RectangleArea("Ciao", new Coords(0, 0), 1, 1);
        assertEquals("Ciao", area.getLabel());
    }
}