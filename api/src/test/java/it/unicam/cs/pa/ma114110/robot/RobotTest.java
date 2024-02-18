package it.unicam.cs.pa.ma114110.robot;

import it.unicam.cs.pa.ma114110.area.RectangleArea;
import it.unicam.cs.pa.ma114110.command.ContinueCommand;
import it.unicam.cs.pa.ma114110.command.SampleCommand;
import it.unicam.cs.pa.ma114110.command.StopCommand;
import it.unicam.cs.pa.ma114110.command.iteration.RepeatCommand;
import it.unicam.cs.pa.ma114110.command.iteration.UntilCommand;
import it.unicam.cs.pa.ma114110.command.move.FollowCommand;
import it.unicam.cs.pa.ma114110.command.move.MoveCommand;
import it.unicam.cs.pa.ma114110.command.move.MoveRandomCommand;
import it.unicam.cs.pa.ma114110.command.program.Program;
import it.unicam.cs.pa.ma114110.command.signal.SignalingCommand;
import it.unicam.cs.pa.ma114110.space.Coords;
import it.unicam.cs.pa.ma114110.space.Direction;
import it.unicam.cs.pa.ma114110.space.Environment;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class RobotTest {
    @Test
    void getCondition() {
        Robot robot = new Robot(new Coords(1, 1), 1);
        assertEquals(Condition.STOP, robot.getCondition());
    }

    @Test
    void getCoords() {
        Coords coords = new Coords(1, 1);
        Robot robot = new Robot(coords, 1);
        assertEquals(coords, robot.getCoords());
    }

    @Test
    void executeNextCommand() {
        Environment environment = new Environment();
        Program program = new Program(environment);

        program.addCommand(new MoveCommand(new Direction(1, 1), 1));
        Robot robot = new Robot(new Coords(1, 1), 1);
        robot.addProgram(program);
        robot.executeNextCommand(1);
        assertEquals(2, robot.getCoords().x());
        assertEquals(2, robot.getCoords().y());
    }

    @Test
    void MoveCommand() {
        Environment environment = new Environment();
        Program program = new Program(environment);

        program.addCommand(new MoveCommand(new Direction(1, 1), 1));
        Robot robot = new Robot(new Coords(1, 1), 1);
        robot.addProgram(program);
        robot.executeNextCommand(1);
        assertEquals(2, robot.getCoords().x());
        assertEquals(2, robot.getCoords().y());
        assertEquals(Condition.MOVE, robot.getCondition());
    }

    @Test
    void MoveRandomCommand() {
        Environment environment = new Environment();
        Program program = new Program(environment);

        program.addCommand(new MoveRandomCommand(new Coords(1, 1), new Coords(2, 2), 1));
        Robot robot = new Robot(new Coords(1, 1), 1);
        robot.addProgram(program);
        robot.executeNextCommand(1);
        assertTrue(robot.getCoords().x() <= 2 && robot.getCoords().y() <= 2);
        assertEquals(Condition.MOVE, robot.getCondition());
    }

    @Test
    void FollowCommand() {
        Environment environment = new Environment();
        Program programM = new Program(environment);
        Program programS = new Program(environment);

        programS.addCommand(new SignalingCommand("signal", true));
        programM.addCommand(new FollowCommand("signal", 20, 1));

        Robot robot = new Robot(new Coords(0, 1), 1);
        environment.addRobot(robot);
        robot.addProgram(programM);

        assertTrue(robot.getCoords().x() < 20 && robot.getCoords().y() < 20);

        Robot robot2 = new Robot(new Coords(0, 4), 1);
        environment.addRobot(robot2);
        robot2.addProgram(programS);

        robot2.executeNextCommand(1);
        robot.executeNextCommand(1);

        assertEquals(0, robot.getCoords().x());
        assertEquals(2, robot.getCoords().y());
        assertEquals(Condition.MOVE, robot.getCondition());
    }

    @Test
    void SignalCommand() {
        Environment environment = new Environment();
        Program program = new Program(environment);

        program.addCommand(new SignalingCommand("signal", true));
        Robot robot = new Robot(new Coords(1, 1), 1);
        robot.addProgram(program);
        robot.executeNextCommand(1);
        assertEquals("signal", robot.getSignal());
    }

    @Test
    void UnSignalCommand() {
        Environment environment = new Environment();
        Program program = new Program(environment);

        program.addCommand(new SignalingCommand("signal", true));
        program.addCommand(new SignalingCommand("signal", false));
        Robot robot = new Robot(new Coords(1, 1), 1);
        robot.addProgram(program);
        robot.executeNextCommand(1);
        robot.executeNextCommand(1);

        assertNull(robot.getSignal());
    }

    @Test
    void StopCommand() {
        Environment environment = new Environment();
        Program program = new Program(environment);

        program.addCommand(new StopCommand());
        Robot robot = new Robot(new Coords(1, 1), 1);
        robot.addProgram(program);
        robot.executeNextCommand(1);
        assertEquals(Condition.STOP, robot.getCondition());
    }

    @Test
    void ContinueCommand() {
        Environment environment = new Environment();
        Program program = new Program(environment);

        program.addCommand(new MoveCommand(new Direction(1, 1), 1));
        program.addCommand(new ContinueCommand(1));
        Robot robot = new Robot(new Coords(1, 1), 1);
        robot.addProgram(program);
        robot.executeNextCommand(1);
        robot.executeNextCommand(1);
        assertEquals(3, robot.getCoords().x());
        assertEquals(3, robot.getCoords().y());
        assertEquals(Condition.MOVE, robot.getCondition());
    }

    @Test
    void UntilCommand() {
        Environment environment = new Environment();
        Program program = new Program(environment);

        program.addCommand(new UntilCommand("signal", getIterationConfig()));

        Robot robot = new Robot(new Coords(1, 1), 1);

        environment.addRobot(robot);
        environment.addArea(new RectangleArea("signal", new Coords(1, 6), 1, 1));

        robot.addProgram(program);
        robot.executeNextCommand(1);

        assertEquals(1, robot.getCoords().x());
        assertEquals(6, robot.getCoords().y());
    }

    @Test
    void RepeatCommand() {
        Environment environment = new Environment();
        Program program = new Program(environment);

        program.addCommand(new RepeatCommand(5, getIterationConfig()));

        Robot robot = new Robot(new Coords(1, 1), 1);

        robot.addProgram(program);
        robot.executeNextCommand(1);

        assertEquals(1, robot.getCoords().x());
        assertEquals(26, robot.getCoords().y());
    }

    private LinkedList<SampleCommand> getIterationConfig (){
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
        Environment environment = new Environment();
        Program program = new Program(environment);

        Robot robot = new Robot(new Coords(1, 1), 1);
        assertThrows(NullPointerException.class, () -> robot.addProgram(null));

        program.addCommand(new MoveCommand(new Direction(1, 1), 1));
        robot.addProgram(program);
        assertEquals(program, robot.getProgram());
    }

    @Test
    void getSignal() {
        Environment environment = new Environment();
        Program program = new Program(environment);

        program.addCommand(new SignalingCommand("signal", true));
        Robot robot = new Robot(new Coords(1, 1), 1);
        robot.addProgram(program);
        robot.executeNextCommand(1);
        assertEquals("signal", robot.getSignal());
    }

    @Test
    void cose() {
        Robot robot = new Robot(new Coords(1, 1), 1);
        Environment environment = new Environment();
        Program program = new Program(environment);

        program.addCommand(new MoveCommand(new Direction(1, 0), 1));
        robot.addProgram(program);
        robot.executeNextCommand(1);

        assertEquals(2, robot.getCoords().x());

        robot.executeNextCommand(1);

        assertEquals(2, robot.getCoords().x());
    }
}