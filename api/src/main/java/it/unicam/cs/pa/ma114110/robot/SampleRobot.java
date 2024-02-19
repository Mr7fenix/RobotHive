package it.unicam.cs.pa.ma114110.robot;

import it.unicam.cs.pa.ma114110.area.SampleArea;
import it.unicam.cs.pa.ma114110.command.Command;
import it.unicam.cs.pa.ma114110.command.ContinueCommand;
import it.unicam.cs.pa.ma114110.command.StopCommand;
import it.unicam.cs.pa.ma114110.command.iteration.ForeverCommand;
import it.unicam.cs.pa.ma114110.command.iteration.IterationCommand;
import it.unicam.cs.pa.ma114110.command.iteration.RepeatCommand;
import it.unicam.cs.pa.ma114110.command.iteration.UntilCommand;
import it.unicam.cs.pa.ma114110.command.move.FollowCommand;
import it.unicam.cs.pa.ma114110.command.move.MoveCommand;
import it.unicam.cs.pa.ma114110.command.move.MoveRandomCommand;
import it.unicam.cs.pa.ma114110.command.program.ProgramInterface;
import it.unicam.cs.pa.ma114110.command.signal.SignalingCommand;
import it.unicam.cs.pa.ma114110.space.coords.SampleCoords;
import it.unicam.cs.pa.ma114110.space.coords.Coords;
import it.unicam.cs.pa.ma114110.space.direction.SampleDirection;
import it.unicam.cs.pa.ma114110.space.enviroment.Environment;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class SampleRobot implements Robot {
    private Coords coords;
    private Condition condition;
    private ProgramInterface program;
    private Command lastCommand;
    private String signal;
    private final int id;


    public SampleRobot(SampleCoords coords, int id) {
        this.condition = Condition.STOP;

        if (coords == null) {
            throw new IllegalArgumentException("Coords cannot be null");
        }

        if (id < 0) {
            throw new IllegalArgumentException("Id cannot be negative");
        }

        this.id = id;
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

        Command command = this.program.getNextCommand();

        if (command instanceof ContinueCommand) {
            continueCommand((ContinueCommand) command, dt);

        } else this.lastCommand = command;

        commandSorter(command, dt);
    }

    @Override
    public void addProgram(ProgramInterface program) {
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
    public ProgramInterface getProgram() {
        return this.program;
    }

    @Override
    public int getId() {
        return this.id;
    }

    /**
     * This method returns the direction from the coordinates by normalizing them.
     *
     * @param coords to normalize in direction
     * @return Direction direction
     */
    private SampleDirection normalizeCoords(Coords coords) {
        double theta = Math.atan2(coords.y() - this.coords.y(), coords.x() - this.coords.x());
        return new SampleDirection(Math.cos(theta), Math.sin(theta));
    }

    /**
     * This method move the robot to the specific direction
     *
     * @param command to move
     * @param dt      delta time
     */
    private void moveToSpecificDirection(MoveCommand command, double dt) {
        if (!(this.condition == Condition.MOVE)) {
            this.condition = Condition.MOVE;
        }

        //Calculate movement distance
        double distance = dt * command.s();

        double x = (coords.x() + (command.direction().getX() * distance));
        double y = (coords.y() + (command.direction().getY() * distance));

        this.coords = new SampleCoords(x, y);
    }


    /**
     * This method move the robot to the random direction
     *
     * @param command to move
     * @param dt      delta time
     */
    private void moveToRandomDirection(MoveRandomCommand command, double dt) {
        double randomX = (int) ((Math.random() * (command.secondPoint().x() - command.firstPoint().x())) + command.firstPoint().x());
        double randomY = (int) ((Math.random() * (command.secondPoint().y() - command.firstPoint().y())) + command.firstPoint().y());

        moveToSpecificDirection(new MoveCommand(normalizeCoords(new SampleCoords(randomX, randomY)), command.s()), dt);
    }

    /**
     * This method set the signal of the robot or unsignal it
     *
     * @param command to set
     */
    private void signaling(SignalingCommand command) {
        if (command.signal()) {
            this.signal = command.label();
        } else {
            if (Objects.equals(this.signal, command.label())) {
                this.signal = null;
            }
        }
    }

    /**
     * This method move the robot to the most close robot
     *
     * @param command to move
     * @param dt      delta time
     */
    private void follow(FollowCommand command, double dt) {
        List<Robot> followersList = new LinkedList<>();

        for (Robot robot : program.getSpace().getRobots()) {
            if (Objects.equals(robot.getSignal(), command.label())) {
                if (Math.sqrt(Math.pow(robot.getCoords().x() - coords.x(), 2) + Math.pow(robot.getCoords().x() - coords.y(), 2)) < command.distance()) {
                    followersList.add(robot);
                }
            }
        }

        if (followersList.isEmpty()) {
            Coords randomCoords = new SampleCoords(-command.distance(), command.distance());
            moveToRandomDirection(new MoveRandomCommand(randomCoords, randomCoords, command.s()), dt);
        } else {
            Coords midCoords = getMidPoint(followersList);
            System.out.println(midCoords.x());
            System.out.println(midCoords.y());
            moveToSpecificDirection(new MoveCommand(normalizeCoords(midCoords), command.s()), dt);
        }
    }

    /**
     * This method returns the most close robot to the robot
     *
     * @param robots robots in specific distance
     * @return the most close robot
     */
    private Coords getMidPoint(List<Robot> robots) {
        double x = 0;
        double y = 0;

        for (Robot robot : robots) {
            x += robot.getCoords().x();
            y += robot.getCoords().y();
        }

        return new SampleCoords(x / robots.size(), y / robots.size());
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
    private void commandSorter(Command command, double dt) {
        switch (command) {
            case MoveRandomCommand cmd -> moveToRandomDirection(cmd, dt);
            case MoveCommand cmd -> moveToSpecificDirection(cmd, dt);
            case SignalingCommand cmd -> signaling(cmd);
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
    private void iterationCommandSorter(IterationCommand command, double dt) {
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

    private void repeat(RepeatCommand cmd, double dt) {
        if (cmd.getTimes() == 0) {
            return;
        }

        program.addFirst(new RepeatCommand(cmd.getTimes() - 1, cmd.getCommandList()));
        for (Command command : cmd.getCommandList().reversed()) {
            program.addFirst(command);
        }

        executeNextCommand(dt);
    }

    /**
     * This method repeat specific program until the specific label is not set
     *
     * @param cmd iteration command
     * @param dt  delta time
     */
    private void until(UntilCommand cmd, double dt) {
        Environment environment = program.getSpace();

        List<SampleArea> sampleAreas = environment.getAreas().stream().filter(area -> area.getLabel().equals(cmd.getLabel())).toList();
        if (!sampleAreas.isEmpty()) {
            if (sampleAreas.stream().noneMatch(area -> area.contains(coords))) {
                program.addFirst(cmd);

                for (Command command : cmd.getCommandList().reversed()) {
                    program.addFirst(command);
                }

                executeNextCommand(dt);
            }
        }
    }

    /**
     * This method repeat specific program forever
     *
     * @param cmd iteration command
     * @param dt  delta time
     */
    private void doForever(ForeverCommand cmd, double dt) {
        program.addFirst(cmd);

        for (Command command : cmd.getCommandList().reversed()) {
            program.addFirst(command);
        }

        executeNextCommand(dt);
    }

    /**
     * This method repeat last command for the specific times
     *
     * @param cmd iteration command
     * @param dt  delta time
     */
    private void continueCommand(ContinueCommand cmd, double dt) {
        if (lastCommand == null) {
            return;
        }

        for (int i = 0; i < cmd.getS(); i++) {
            program.addFirst(lastCommand);
        }

        executeNextCommand(dt);
    }
}