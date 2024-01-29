package it.unicam.cs.pa.ma114110.robot;

import it.unicam.cs.pa.ma114110.area.SquaredArea;
import it.unicam.cs.pa.ma114110.command.ContinueCommand;
import it.unicam.cs.pa.ma114110.command.SampleCommand;
import it.unicam.cs.pa.ma114110.command.StopCommand;
import it.unicam.cs.pa.ma114110.command.iteration.RepeatCommand;
import it.unicam.cs.pa.ma114110.command.iteration.UntilCommand;
import it.unicam.cs.pa.ma114110.command.move.FollowCommand;
import it.unicam.cs.pa.ma114110.command.move.MoveCommand;
import it.unicam.cs.pa.ma114110.command.move.MoveRandomCommand;
import it.unicam.cs.pa.ma114110.command.program.Program;
import it.unicam.cs.pa.ma114110.command.signal.SignalCommand;
import it.unicam.cs.pa.ma114110.command.signal.UnSignalCommand;
import it.unicam.cs.pa.ma114110.space.Coords;
import it.unicam.cs.pa.ma114110.space.Direction;
import it.unicam.cs.pa.ma114110.space.Space;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class RobotTest {
    @Test
    void getCondition() {
        Robot robot = new Robot(new Coords(1, 1));
        assertEquals(Condition.STOP, robot.getCondition());
    }

    @Test
    void getCoords() {
        Coords coords = new Coords(1, 1);
        Robot robot = new Robot(coords);
        assertEquals(coords, robot.getCoords());
    }

    @Test
    void executeNextCommand() {
        Space space = new Space();
        Program program = new Program(space);

        program.addCommand(new MoveCommand(new Direction(1, 1), 1));
        Robot robot = new Robot(new Coords(1, 1));
        robot.addProgram(program);
        robot.executeNextCommand(1);
        assertEquals(2, robot.getCoords().getX());
        assertEquals(2, robot.getCoords().getY());
    }

    @Test
    void MoveCommand() {
        Space space = new Space();
        Program program = new Program(space);

        program.addCommand(new MoveCommand(new Direction(1, 1), 1));
        Robot robot = new Robot(new Coords(1, 1));
        robot.addProgram(program);
        robot.executeNextCommand(1);
        assertEquals(2, robot.getCoords().getX());
        assertEquals(2, robot.getCoords().getY());
        assertEquals(Condition.MOVE, robot.getCondition());
    }

    @Test
    void MoveRandomCommand() {
        Space space = new Space();
        Program program = new Program(space);

        program.addCommand(new MoveRandomCommand(new Coords(1, 1), new Coords(2, 2), 1));
        Robot robot = new Robot(new Coords(1, 1));
        robot.addProgram(program);
        robot.executeNextCommand(1);
        assertTrue(robot.getCoords().getX() <= 2 && robot.getCoords().getY() <= 2);
        assertEquals(Condition.MOVE, robot.getCondition());
    }

    @Test
    void FollowCommand() {
        Space space = new Space();
        Program programM = new Program(space);
        Program programS = new Program(space);

        programS.addCommand(new SignalCommand("signal"));
        programM.addCommand(new FollowCommand("signal", 20, 1));

        Robot robot = new Robot(new Coords(0, 1));
        space.addRobot(robot);
        robot.addProgram(programM);

        assertTrue(robot.getCoords().getX() < 20 && robot.getCoords().getY() < 20);

        Robot robot2 = new Robot(new Coords(0, 4));
        space.addRobot(robot2);
        robot2.addProgram(programS);

        robot2.executeNextCommand(1);
        robot.executeNextCommand(1);

        assertEquals(0, robot.getCoords().getX());
        assertEquals(2, robot.getCoords().getY());
        assertEquals(Condition.MOVE, robot.getCondition());
    }

    @Test
    void SignalCommand() {
        Space space = new Space();
        Program program = new Program(space);

        program.addCommand(new SignalCommand("signal"));
        Robot robot = new Robot(new Coords(1, 1));
        robot.addProgram(program);
        robot.executeNextCommand(1);
        assertEquals("signal", robot.getSignal());
    }

    @Test
    void UnSignalCommand() {
        Space space = new Space();
        Program program = new Program(space);

        program.addCommand(new SignalCommand("signal"));
        program.addCommand(new UnSignalCommand("signal"));
        Robot robot = new Robot(new Coords(1, 1));
        robot.addProgram(program);
        robot.executeNextCommand(1);
        robot.executeNextCommand(1);

        assertNull(robot.getSignal());
    }

    @Test
    void StopCommand() {
        Space space = new Space();
        Program program = new Program(space);

        program.addCommand(new StopCommand());
        Robot robot = new Robot(new Coords(1, 1));
        robot.addProgram(program);
        robot.executeNextCommand(1);
        assertEquals(Condition.STOP, robot.getCondition());
    }

    @Test
    void ContinueCommand() {
        Space space = new Space();
        Program program = new Program(space);

        program.addCommand(new MoveCommand(new Direction(1, 1), 1));
        program.addCommand(new ContinueCommand());
        Robot robot = new Robot(new Coords(1, 1));
        robot.addProgram(program);
        robot.executeNextCommand(1);
        robot.executeNextCommand(1);
        assertEquals(3, robot.getCoords().getX());
        assertEquals(3, robot.getCoords().getY());
        assertEquals(Condition.MOVE, robot.getCondition());
    }

    @Test
    void UntilCommand() {
        Space space = new Space();
        Program program = new Program(space);

        program.addCommand(new UntilCommand("signal", getIterationConfig()));

        Robot robot = new Robot(new Coords(1, 1));

        space.addRobot(robot);
        space.addArea(new SquaredArea("signal", new Coords(1, 6), 1, 1));

        robot.addProgram(program);
        robot.executeNextCommand(1);

        assertEquals(1, robot.getCoords().getX());
        assertEquals(6, robot.getCoords().getY());
    }

    @Test
    void RepeatCommand() {
        Space space = new Space();
        Program program = new Program(space);

        program.addCommand(new RepeatCommand(5, getIterationConfig()));

        Robot robot = new Robot(new Coords(1, 1));

        robot.addProgram(program);
        robot.executeNextCommand(1);

        assertEquals(1, robot.getCoords().getX());
        assertEquals(26, robot.getCoords().getY());
    }

    private LinkedList<SampleCommand> getIterationConfig (){
        Space space = new Space();
        LinkedList<SampleCommand> commandList = new LinkedList<>();

        commandList.add(new MoveCommand(new Direction(0, 1), 1));
        commandList.add(new MoveCommand(new Direction(0, 1), 1));
        commandList.add(new MoveCommand(new Direction(0, 1), 1));
        commandList.add(new MoveCommand(new Direction(0, 1), 1));
        commandList.add(new MoveCommand(new Direction(0, 1), 1));

        return commandList;
    }

    @Test
    void addProgram() {
        Space space = new Space();
        Program program = new Program(space);

        Robot robot = new Robot(new Coords(1, 1));
        assertThrows(NullPointerException.class, () -> robot.addProgram(null));

        program.addCommand(new MoveCommand(new Direction(1, 1), 1));
        robot.addProgram(program);
        assertEquals(program, robot.getProgram());
    }

    @Test
    void getSignal() {
        Space space = new Space();
        Program program = new Program(space);

        program.addCommand(new SignalCommand("signal"));
        Robot robot = new Robot(new Coords(1, 1));
        robot.addProgram(program);
        robot.executeNextCommand(1);
        assertEquals("signal", robot.getSignal());
    }
}