package it.unicam.cs.pa.ma114110.robot;

import it.unicam.cs.pa.ma114110.Simulator;
import it.unicam.cs.pa.ma114110.command.move.FollowCommand;
import it.unicam.cs.pa.ma114110.command.move.MoveCommand;
import it.unicam.cs.pa.ma114110.command.move.MoveRandomCommand;
import it.unicam.cs.pa.ma114110.command.signal.SignalCommand;
import it.unicam.cs.pa.ma114110.command.signal.UnSignalCommand;
import it.unicam.cs.pa.ma114110.space.Coords;
import it.unicam.cs.pa.ma114110.space.Direction;
import it.unicam.cs.pa.ma114110.space.Space;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RobotTest {

    @Test
    void getCondition() {
        Robot robot = new Robot(new Coords(0, 0));
        assertEquals(Condition.STOP, robot.getCondition());
    }

    @Test
    void moveToSpecificDirection() {
        Robot robot = new Robot(new Coords(0, 0));
        robot.moveToSpecificDirection(new MoveCommand(new Direction(1, -1), 5), 1.0);
        assertEquals(Condition.MOVE, robot.getCondition());
        assertEquals(5, robot.getCoords().getX());
        assertEquals(-5, robot.getCoords().getY());
    }

    @Test
    void moveToRandomDirection() {
        Robot robot = new Robot(new Coords(0, 0));
        robot.moveToRandomDirection(new MoveRandomCommand(new Coords(-5, -5), new Coords(5, 5), 5), 1.0);
        assertEquals(Condition.MOVE, robot.getCondition());
        assertTrue(robot.getCoords().getX() >= -5 && robot.getCoords().getX() <= 5);
        assertTrue(robot.getCoords().getY() >= -5 && robot.getCoords().getY() <= 5);
    }

    @Test
    void signal() {
        Robot robot = new Robot(new Coords(0, 0));
        robot.signal(new SignalCommand("test"));
        assertEquals("test", robot.getSignal());
    }

    @Test
    void unSignal() {
        Robot robot = new Robot(new Coords(0, 0));
        robot.signal(new SignalCommand("test"));
        assertEquals("test", robot.getSignal());

        robot.unSignal(new UnSignalCommand("test"));
        assertNull(robot.getSignal());
    }

    @Test
    void follow() {
        Space space = new Space();
        Robot robot = new Robot(new Coords(0, 0));
        Robot robot2 = new Robot(new Coords(0, 20));

        space.addRobot(robot);
        space.addRobot(robot2);

        robot.addCommands(new FollowCommand("test", 25, 10));
        robot2.addCommands(new SignalCommand("test"));

        robot2.executeCommand(1.0, space);
        robot.executeCommand(1.0, space);

        Simulator simulator = new Simulator(space);
        simulator.simulate(1.0, 20);

        assertEquals(Condition.MOVE, robot.getCondition());
        assertEquals(0, robot.getCoords().getX());
        assertEquals(10, robot.getCoords().getY());
    }

    @Test
    void stop() {
        Robot robot = new Robot(new Coords(0, 0));
        robot.moveToSpecificDirection(new MoveCommand(new Direction(1, -1), 5), 1.0);
        assertEquals(Condition.MOVE, robot.getCondition());
        assertEquals(5, robot.getCoords().getX());
        assertEquals(-5, robot.getCoords().getY());

        robot.stop();
        assertEquals(Condition.STOP, robot.getCondition());
    }

    @Test
    void getCoords() {
        Robot robot = new Robot(new Coords(0, 0));
        assertEquals(0, robot.getCoords().getX());
        assertEquals(0, robot.getCoords().getY());
    }

    @Test
    void executeCommand() {
        Robot robot = new Robot(new Coords(0, 0));
        robot.moveToSpecificDirection(new MoveCommand(new Direction(1, -1), 5), 1.0);
        assertEquals(Condition.MOVE, robot.getCondition());
        assertEquals(5, robot.getCoords().getX());
        assertEquals(-5, robot.getCoords().getY());

        robot.stop();
        robot.executeCommand(1.0, null);
        assertEquals(Condition.STOP, robot.getCondition());
    }

    @Test
    void addCommands() {
        Robot robot = new Robot(new Coords(0, 0));
        robot.addCommands(new MoveCommand(new Direction(1, -1), 5));

        robot.executeCommand(1.0, null);
        assertEquals(Condition.MOVE, robot.getCondition());
        assertEquals(5, robot.getCoords().getX());
    }
}