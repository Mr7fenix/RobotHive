package it.unicam.cs.pa.ma114110.parser;

import it.unicam.cs.pa.ma114110.area.CircularArea;
import it.unicam.cs.pa.ma114110.area.RectangleArea;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EnvironmentParserTest {

    @Test
    void parse() {
        EnvironmentParser parser = new EnvironmentParser();
        assertEquals(1, parser.parse("../api/src/test/resources/parserTest/test_RECTANGLE_AREA").getAreas().size());
    }

    @Test
    void parseRectangle() {
        EnvironmentParser parser = new EnvironmentParser();
        assertInstanceOf(RectangleArea.class, parser.parse("../api/src/test/resources/parserTest/test_RECTANGLE_AREA").getAreas().getFirst());
    }

    @Test
    void parseCircle() {
        EnvironmentParser parser = new EnvironmentParser();
        assertInstanceOf(CircularArea.class, parser.parse("../api/src/test/resources/parserTest/test_CIRCLE_AREA").getAreas().getFirst());
    }
}