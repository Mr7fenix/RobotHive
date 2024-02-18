package it.unicam.cs.pa.ma114110.space;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CoordsTest {

    @Test
    void getX() {
        CoordsInterface coords = new Coords(1, 2);
        assertEquals(1, coords.x());
    }

    @Test
    void getY() {
        CoordsInterface coords = new Coords(1, 2);
        assertEquals(2, coords.y());
    }
}