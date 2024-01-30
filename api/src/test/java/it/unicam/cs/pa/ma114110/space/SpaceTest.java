package it.unicam.cs.pa.ma114110.space;

import it.unicam.cs.pa.ma114110.area.SquaredArea;
import it.unicam.cs.pa.ma114110.robot.Robot;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SpaceTest {

    @Test
    void addRobot() {
        Environment environment = new Environment();
        assertThrowsExactly(IllegalArgumentException.class, () -> environment.addRobot(null));
        environment.addRobot(new Robot(new Coords(0, 0)));
        assertEquals(1, environment.getRobots().size());
    }

    @Test
    void getRobots() {
        Environment environment = new Environment();
        environment.addRobot(new Robot(new Coords(0, 0)));
        assertEquals(1, environment.getRobots().size());
    }

    @Test
    void getRobotByCoords() {
        Environment environment = new Environment();
        Robot robot = new Robot(new Coords(0, 0));
        environment.addRobot(robot);
        assertEquals(robot, environment.getRobotByCoords(new Coords(0, 0)));
    }

    @Test
    void removeRobot() {
        Environment environment = new Environment();
        Robot robot = new Robot(new Coords(0, 0));
        environment.addRobot(robot);
        environment.removeRobot(robot);
        assertEquals(0, environment.getRobots().size());

        assertThrowsExactly(IllegalArgumentException.class, () -> environment.removeRobot(null));
    }

    @Test
    void addArea() {
        Environment environment = new Environment();
        assertThrowsExactly(IllegalArgumentException.class, () -> environment.addArea(null));
        environment.addArea(new SquaredArea("Ciao", new Coords(0, 0), 1, 1));
        assertEquals(1, environment.getAreas().size());
    }
}