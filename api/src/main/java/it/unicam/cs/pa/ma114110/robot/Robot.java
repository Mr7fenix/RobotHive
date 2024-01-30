package it.unicam.cs.pa.ma114110.robot;

import it.unicam.cs.pa.ma114110.command.ContinueCommand;
import it.unicam.cs.pa.ma114110.command.SampleCommand;
import it.unicam.cs.pa.ma114110.command.StopCommand;
import it.unicam.cs.pa.ma114110.command.iteration.ForeverCommand;
import it.unicam.cs.pa.ma114110.command.iteration.IterationCommand;
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
import it.unicam.cs.pa.ma114110.space.Environment;

import java.util.Objects;

public class Robot implements RobotInterface {

    //Coords of the robot
    private Coords coords;

    //Condition of the robot (STOP or MOVE)
    private Condition condition;

    //Stack of commands
    private Program program;

    //Last Saved Command
    private SampleCommand lastCommand;

    //Signal of the robot
    private String signal;


    public Robot(Coords coords) {
        this.condition = Condition.STOP;

        if (coords == null) {
            throw new IllegalArgumentException("Coords cannot be null");
        }

        this.coords = coords;

    }

    @Override
    public Condition getCondition() {
        return this.condition;
    }

    @Override
    public Coords getCoords() {
        return this.coords;
    }

    @Override
    public void executeNextCommand(double dt) {
        if (this.program == null || this.program.size() == 0) {
            return;
        }

        SampleCommand command = this.program.getNextCommand();

        if (command instanceof ContinueCommand) {
            continueCommand((ContinueCommand) command, dt);

        } else this.lastCommand = command;

        commandSorter(command, dt);
    }

    @Override
    public void addProgram(Program program) {
        if (program == null || program.size() == 0) {
            throw new NullPointerException("Program cannot be null ore empty");
        }

        this.program = program;
    }

    @Override
    public String getSignal() {
        return this.signal;
    }

    @Override
    public Program getProgram() {
        return this.program;
    }

    /**
     * This method returns the direction from the coordinates by normalizing them.
     *
     * @param coords to normalize in direction
     * @return Direction direction
     */
    private Direction normalizeCoords(Coords coords) {
        double magnitude = Math.sqrt(Math.pow(coords.getX(), 2) + Math.pow(coords.getY(), 2));

        if (magnitude == 0) {
            throw new IllegalArgumentException("coords must be different from (0, 0)");
        }

        return new Direction((coords.getX() / magnitude), (coords.getY() / magnitude));
    }

    /**
     * This method move the robot to the specific direction
     *
     * @param command to move
     * @param dt      delta time
     */
    private void moveToSpecificDirection(MoveCommand command, Double dt) {
        if (!(this.condition == Condition.MOVE)) {
            this.condition = Condition.MOVE;
        }

        //Calculate movement distance
        double distance = dt * command.getS();

        double x = (coords.getX() + (command.getDirection().getX() * distance));
        double y = (coords.getY() + (command.getDirection().getY() * distance));

        this.coords = new Coords(x, y);
    }


    /**
     * This method move the robot to the random direction
     *
     * @param command to move
     * @param dt      delta time
     */
    private void moveToRandomDirection(MoveRandomCommand command, Double dt) {
        double randomX = (int) ((Math.random() * (command.getSecondPoint().getX() - command.getFirstPoint().getX())) + command.getFirstPoint().getX());
        double randomY = (int) ((Math.random() * (command.getSecondPoint().getY() - command.getFirstPoint().getY())) + command.getFirstPoint().getY());

        moveToSpecificDirection(new MoveCommand(normalizeCoords(new Coords(randomX, randomY)), command.getS()), dt);
    }

    /**
     * This method set the signal of the robot
     *
     * @param command to set
     */
    private void signal(SignalCommand command) {
        this.signal = command.getLabel();
    }

    /**
     * This method unset the signal of the robot
     *
     * @param command to unset
     */
    private void unSignal(UnSignalCommand command) {
        if (Objects.equals(this.signal, command.getLabel())) {
            this.signal = null;
        }
    }

    /**
     * This method move the robot to the most close robot
     *
     * @param command to move
     * @param dt      delta time
     */
    private void follow(FollowCommand command, Double dt) {
        Robot followRobot = getMostCloseRobot(command.getDistance());
        double x = 0;
        double y = 0;

        //If there is no robot ad the distance, robot move to the random direction between -distance and distance
        if (followRobot == null) {
            x = ((Math.random() * (command.getDistance() - (-command.getDistance()))) + (-command.getDistance()));
            y = ((Math.random() * (command.getDistance() - (-command.getDistance()))) + (-command.getDistance()));
        }

        //Robot move to the middle of the two robots
        if (followRobot != null) {
            x = ((coords.getX() + followRobot.getCoords().getX()) / 2);
            y = ((coords.getY() + followRobot.getCoords().getY()) / 2);

        }

        moveToSpecificDirection(new MoveCommand(normalizeCoords(new Coords(x, y)), command.getS()), dt);
    }

    /**
     * This method returns the most close robot to the robot
     *
     * @param distance max distance to search
     * @return the most close robot
     */
    private Robot getMostCloseRobot(double distance) {
        double maxX = coords.getX() + distance;
        double maxY = coords.getY() + distance;

        Robot followRobot = null;

        for (Robot robot : program.getSpace().getRobots()) {
            if (followRobot == null) {
                if (robot.getCoords().getX() <= maxX && robot.getCoords().getY() <= maxY && robot != this) {
                    followRobot = robot;
                }
            } else if (robot.getCoords().getX() <= followRobot.getCoords().getX() || robot.getCoords().getY() <= followRobot.getCoords().getY()) {
                followRobot = robot;
            }
        }

        return followRobot;
    }

    /**
     * This method stop the robot
     */
    private void stop() {
        this.condition = Condition.STOP;
    }


    /**
     * This method sort the command to the specific method
     * Commands are: MoveRandomCommand, MoveCommand, UnSignalCommand, SignalCommand, FollowCommand, StopCommand, IterationCommand
     *
     * @param command to sort
     * @param dt      delta time
     */
    private void commandSorter(SampleCommand command, Double dt) {
        switch (command) {
            case MoveRandomCommand cmd -> moveToRandomDirection(cmd, dt);
            case MoveCommand cmd -> moveToSpecificDirection(cmd, dt);
            case UnSignalCommand cmd -> unSignal(cmd);
            case SignalCommand cmd -> signal(cmd);
            case FollowCommand cmd -> follow(cmd, dt);
            case StopCommand _ -> stop();
            case IterationCommand cmd -> iterationCommandSorter(cmd, dt);
            default -> throw new IllegalStateException(STR."Unexpected value: \{command}");
        }
    }

    /**
     * This method sort the iteration command to the specific method.
     * Iteration command are: Repeat, Until, DoForever
     *
     * @param command to sort
     * @param dt      delta time
     */
    private void iterationCommandSorter(IterationCommand command, Double dt) {
        switch (command) {
            case RepeatCommand cmd -> repeat(cmd, dt);
            case UntilCommand cmd -> until(cmd, dt);
            case ForeverCommand cmd -> doForever(cmd, dt);
            default -> throw new IllegalStateException(STR."Unexpected value: \{command}");
        }
    }

    /**
     * This method repeat specific program for the specific times
     *
     * @param cmd iteration command
     * @param dt  delta time
     */

    private void repeat(RepeatCommand cmd, Double dt) {
        for (int i = 0; i < cmd.getTimes(); i++) {
            for (SampleCommand command : cmd.getCommandList()) {
                commandSorter(command, dt);
            }
        }
    }

    /**
     * This method repeat specific program until the specific label is not set
     *
     * @param cmd iteration command
     * @param dt  delta time
     */
    private void until(UntilCommand cmd, Double dt) {
        Environment environment = program.getSpace();
        environment.getAreas().forEach(area -> {
            if (area.getLabel().equals(cmd.getLabel())) {
                if (!area.contains(coords)) {
                    for (SampleCommand command : cmd.getCommandList()) {
                        commandSorter(command, dt);
                    }
                }
            }
        });
    }

    /**
     * This method repeat specific program forever
     *
     * @param cmd iteration command
     * @param dt  delta time
     */
    private void doForever(ForeverCommand cmd, Double dt) {
        program.addCommand(cmd);

        for (SampleCommand command : cmd.getCommandList()) {
            commandSorter(command, dt);
        }

        executeNextCommand(dt);
    }

    /**
     * This method repeat last command for the specific times
     *
     * @param cmd iteration command
     * @param dt  delta time
     */
    private void continueCommand(ContinueCommand cmd, Double dt) {
        if (lastCommand == null) {
            return;
        }

        for (int i = 0; i < cmd.getS(); i++) {
            program.addFirst(lastCommand);
        }

        executeNextCommand(dt);
    }
}