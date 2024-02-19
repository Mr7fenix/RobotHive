package it.unicam.cs.pa.ma114110.robot;

import it.unicam.cs.pa.ma114110.area.RectangleSampleArea;
import it.unicam.cs.pa.ma114110.command.Command;
import it.unicam.cs.pa.ma114110.command.ContinueCommand;
import it.unicam.cs.pa.ma114110.command.StopCommand;
import it.unicam.cs.pa.ma114110.command.iteration.RepeatCommand;
import it.unicam.cs.pa.ma114110.command.iteration.UntilCommand;
import it.unicam.cs.pa.ma114110.command.move.FollowCommand;
import it.unicam.cs.pa.ma114110.command.move.MoveCommand;
import it.unicam.cs.pa.ma114110.command.move.MoveRandomCommand;
import it.unicam.cs.pa.ma114110.command.program.Program;
import it.unicam.cs.pa.ma114110.command.signal.SignalingCommand;
import it.unicam.cs.pa.ma114110.space.coords.SampleCoords;
import it.unicam.cs.pa.ma114110.space.direction.SampleDirection;
import it.unicam.cs.pa.ma114110.space.enviroment.SampleEnvironment;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class SampleRobotTest {
    @Test
    void getCondition() {
        SampleRobot robot = new SampleRobot(new SampleCoords(1, 1), 1);
        assertEquals(Condition.STOP, robot.getCondition());
    }

    @Test
    void getCoords() {
        SampleCoords coords = new SampleCoords(1, 1);
        SampleRobot robot = new SampleRobot(coords, 1);
        assertEquals(coords, robot.getCoords());
    }

    @Test
    void executeNextCommand() {
        SampleEnvironment environment = new SampleEnvironment();
        Program program = new Program(environment);

        program.addCommand(new MoveCommand(new SampleDirection(1, 1), 1));
        SampleRobot robot = new SampleRobot(new SampleCoords(1, 1), 1);
        robot.addProgram(program);
        robot.executeNextCommand(1);
        assertEquals(2, robot.getCoords().x());
        assertEquals(2, robot.getCoords().y());
    }

    @Test
    void MoveCommand() {
        SampleEnvironment environment = new SampleEnvironment();
        Program program = new Program(environment);

        program.addCommand(new MoveCommand(new SampleDirection(1, 1), 1));
        SampleRobot robot = new SampleRobot(new SampleCoords(1, 1), 1);
        robot.addProgram(program);
        robot.executeNextCommand(1);
        assertEquals(2, robot.getCoords().x());
        assertEquals(2, robot.getCoords().y());
        assertEquals(Condition.MOVE, robot.getCondition());
    }

    @Test
    void MoveRandomCommand() {
        SampleEnvironment environment = new SampleEnvironment();
        Program program = new Program(environment);

        program.addCommand(new MoveRandomCommand(new SampleCoords(1, 1), new SampleCoords(2, 2), 1));
        SampleRobot robot = new SampleRobot(new SampleCoords(1, 1), 1);
        robot.addProgram(program);
        robot.executeNextCommand(1);
        assertTrue(robot.getCoords().x() <= 2 && robot.getCoords().y() <= 2);
        assertEquals(Condition.MOVE, robot.getCondition());
    }

    @Test
    void FollowCommand() {
        SampleEnvironment environment = new SampleEnvironment();
        Program programM = new Program(environment);
        Program programS = new Program(environment);

        programS.addCommand(new SignalingCommand("signal", true));
        programM.addCommand(new FollowCommand("signal", 20, 1));

        SampleRobot robot = new SampleRobot(new SampleCoords(0, 1), 1);
        environment.addRobot(robot);
        robot.addProgram(programM);

        assertTrue(robot.getCoords().x() < 20 && robot.getCoords().y() < 20);

        SampleRobot robot2 = new SampleRobot(new SampleCoords(0, 4), 1);
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
        SampleEnvironment environment = new SampleEnvironment();
        Program program = new Program(environment);

        program.addCommand(new SignalingCommand("signal", true));
        SampleRobot robot = new SampleRobot(new SampleCoords(1, 1), 1);
        robot.addProgram(program);
        robot.executeNextCommand(1);
        assertEquals("signal", robot.getSignal());
    }

    @Test
    void UnSignalCommand() {
        SampleEnvironment environment = new SampleEnvironment();
        Program program = new Program(environment);

        program.addCommand(new SignalingCommand("signal", true));
        program.addCommand(new SignalingCommand("signal", false));
        SampleRobot robot = new SampleRobot(new SampleCoords(1, 1), 1);
        robot.addProgram(program);
        robot.executeNextCommand(1);
        robot.executeNextCommand(1);

        assertNull(robot.getSignal());
    }

    @Test
    void StopCommand() {
        SampleEnvironment environment = new SampleEnvironment();
        Program program = new Program(environment);

        program.addCommand(new StopCommand());
        SampleRobot robot = new SampleRobot(new SampleCoords(1, 1), 1);
        robot.addProgram(program);
        robot.executeNextCommand(1);
        assertEquals(Condition.STOP, robot.getCondition());
    }

    @Test
    void ContinueCommand() {
        SampleEnvironment environment = new SampleEnvironment();
        Program program = new Program(environment);

        program.addCommand(new MoveCommand(new SampleDirection(1, 1), 1));
        program.addCommand(new ContinueCommand(1));
        SampleRobot robot = new SampleRobot(new SampleCoords(1, 1), 1);
        robot.addProgram(program);
        robot.executeNextCommand(1);
        robot.executeNextCommand(1);
        assertEquals(3, robot.getCoords().x());
        assertEquals(3, robot.getCoords().y());
        assertEquals(Condition.MOVE, robot.getCondition());
    }

    @Test
    void UntilCommand() {
        SampleEnvironment environment = new SampleEnvironment();
        Program program = new Program(environment);

        program.addCommand(new UntilCommand("signal", getIterationConfig()));

        SampleRobot robot = new SampleRobot(new SampleCoords(1, 1), 1);

        environment.addRobot(robot);
        environment.addArea(new RectangleSampleArea("signal", new SampleCoords(1, 6), 1, 1));

        robot.addProgram(program);
        robot.executeNextCommand(1);

        assertEquals(1, robot.getCoords().x());
        assertEquals(6, robot.getCoords().y());
    }

    @Test
    void RepeatCommand() {
        SampleEnvironment environment = new SampleEnvironment();
        Program program = new Program(environment);

        program.addCommand(new RepeatCommand(5, getIterationConfig()));

        SampleRobot robot = new SampleRobot(new SampleCoords(1, 1), 1);

        robot.addProgram(program);
        robot.executeNextCommand(1);

        assertEquals(1, robot.getCoords().x());
        assertEquals(26, robot.getCoords().y());
    }

    private LinkedList<SampleCommand> getIterationConfig (){
        LinkedList<SampleCommand> commandList = new LinkedList<>();

        commandList.add(new MoveCommand(new SampleDirection(0, 1), 1));
        commandList.add(new MoveCommand(new SampleDirection(0, 1), 1));
        commandList.add(new MoveCommand(new SampleDirection(0, 1), 1));
        commandList.add(new MoveCommand(new SampleDirection(0, 1), 1));
        commandList.add(new MoveCommand(new SampleDirection(0, 1), 1));

        return commandList;
    }

    @Test
    void addProgram() {
        SampleEnvironment environment = new SampleEnvironment();
        Program program = new Program(environment);

        SampleRobot robot = new SampleRobot(new SampleCoords(1, 1), 1);
        assertThrows(NullPointerException.class, () -> robot.addProgram(null));

        program.addCommand(new MoveCommand(new SampleDirection(1, 1), 1));
        robot.addProgram(program);
        assertEquals(program, robot.getProgram());
    }

    @Test
    void getSignal() {
        SampleEnvironment environment = new SampleEnvironment();
        Program program = new Program(environment);

        program.addCommand(new SignalingCommand("signal", true));
        SampleRobot robot = new SampleRobot(new SampleCoords(1, 1), 1);
        robot.addProgram(program);
        robot.executeNextCommand(1);
        assertEquals("signal", robot.getSignal());
    }

    @Test
    void cose() {
        SampleRobot robot = new SampleRobot(new SampleCoords(1, 1), 1);
        SampleEnvironment environment = new SampleEnvironment();
        Program program = new Program(environment);

        program.addCommand(new MoveCommand(new SampleDirection(1, 0), 1));
        robot.addProgram(program);
        robot.executeNextCommand(1);

        assertEquals(2, robot.getCoords().x());

        robot.executeNextCommand(1);

        assertEquals(2, robot.getCoords().x());
    }
}