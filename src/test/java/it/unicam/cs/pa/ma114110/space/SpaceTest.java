package it.unicam.cs.pa.ma114110.space;

import it.unicam.cs.pa.ma114110.area.Area;
import it.unicam.cs.pa.ma114110.area.SquaredArea;
import it.unicam.cs.pa.ma114110.robot.Robot;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SpaceTest {

    @Test
    void addRobot() {
        Space space = new Space();
        assertThrowsExactly(IllegalArgumentException.class, () -> space.addRobot(null));
        space.addRobot(new Robot(new Coords(0, 0)));
        assertEquals(1, space.getRobots().size());
    }

    @Test
    void getRobots() {
        Space space = new Space();
        space.addRobot(new Robot(new Coords(0, 0)));
        assertEquals(1, space.getRobots().size());
    }

    @Test
    void getRobotByCoords() {
        Space space = new Space();
        Robot robot = new Robot(new Coords(0, 0));
        space.addRobot(robot);
        assertEquals(robot, space.getRobotByCoords(new Coords(0, 0)));
    }

    @Test
    void removeRobot() {
        Space space = new Space();
        Robot robot = new Robot(new Coords(0, 0));
        space.addRobot(robot);
        space.removeRobot(robot);
        assertEquals(0, space.getRobots().size());

        assertThrowsExactly(IllegalArgumentException.class, () -> space.removeRobot(null));
    }

    @Test
    void addArea() {
        Space space = new Space();
        assertThrowsExactly(IllegalArgumentException.class, () -> space.addArea(null));
        space.addArea(new SquaredArea("Ciao", new Coords(0, 0), new Coords(1, 1)));
        assertEquals(1, space.getAreas().size());
    }
}