package it.unicam.cs.pa.ma114110.space;

import it.unicam.cs.pa.ma114110.area.RectangleSampleArea;
import it.unicam.cs.pa.ma114110.robot.SampleRobot;
import it.unicam.cs.pa.ma114110.space.coords.SampleCoords;
import it.unicam.cs.pa.ma114110.space.enviroment.SampleEnvironment;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SpaceTest {

    @Test
    void addRobot() {
        SampleEnvironment environment = new SampleEnvironment();
        assertThrowsExactly(IllegalArgumentException.class, () -> environment.addRobot((SampleRobot) null));
        environment.addRobot(new SampleRobot(new SampleCoords(0, 0), 1));
        assertEquals(1, environment.getRobots().size());
    }

    @Test
    void getRobots() {
        SampleEnvironment environment = new SampleEnvironment();
        environment.addRobot(new SampleRobot(new SampleCoords(0, 0), 1));
        assertEquals(1, environment.getRobots().size());
    }

    @Test
    void getRobotByCoords() {
        SampleEnvironment environment = new SampleEnvironment();
        SampleRobot robot = new SampleRobot(new SampleCoords(0, 0), 1);
        environment.addRobot(robot);
        assertEquals(robot, environment.getRobotByCoords(new SampleCoords(0, 0)));
    }

    @Test
    void removeRobot() {
        SampleEnvironment environment = new SampleEnvironment();
        SampleRobot robot = new SampleRobot(new SampleCoords(0, 0), 1);
        environment.addRobot(robot);
        environment.removeRobot(robot);
        assertEquals(0, environment.getRobots().size());

        assertThrowsExactly(IllegalArgumentException.class, () -> environment.removeRobot(null));
    }

    @Test
    void addArea() {
        SampleEnvironment environment = new SampleEnvironment();
        assertThrowsExactly(IllegalArgumentException.class, () -> environment.addArea(null));
        environment.addArea(new RectangleSampleArea("Ciao", new SampleCoords(0, 0), 1, 1));
        assertEquals(1, environment.getAreas().size());
    }
}