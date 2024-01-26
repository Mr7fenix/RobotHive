package it.unicam.cs.pa.ma114110.robot;

import it.unicam.cs.pa.ma114110.command.ContinueCommand;
import it.unicam.cs.pa.ma114110.command.SampleCommand;
import it.unicam.cs.pa.ma114110.command.StopCommand;
import it.unicam.cs.pa.ma114110.command.move.FollowCommand;
import it.unicam.cs.pa.ma114110.command.signal.SignalCommand;
import it.unicam.cs.pa.ma114110.command.move.MoveCommand;
import it.unicam.cs.pa.ma114110.command.move.MoveRandomCommand;
import it.unicam.cs.pa.ma114110.command.signal.UnSignalCommand;
import it.unicam.cs.pa.ma114110.space.Coords;
import it.unicam.cs.pa.ma114110.space.Direction;
import it.unicam.cs.pa.ma114110.space.Space;

import java.util.LinkedList;
import java.util.Objects;

public class Robot implements RobotInterface {

    //Coords of the robot
    private Coords coords;

    //Condition of the robot (STOP or MOVE)
    private Condition condition;

    //Stack of commands
    private final LinkedList<SampleCommand> commandList = new LinkedList<>();

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
    public void moveToSpecificDirection(MoveCommand command, Double dt) {
        if (!(this.condition == Condition.MOVE)) {
            this.condition = Condition.MOVE;
        }

        //Calculate movement distance
        double distance = dt * command.getS();

        double x = (coords.getX() + (command.getDirection().getX() * distance));
        double y = (coords.getY() + (command.getDirection().getY() * distance));

        this.coords = new Coords(x, y);
    }

    @Override
    public void moveToRandomDirection(MoveRandomCommand command, Double dt) {
        double randomX = (int) ((Math.random() * (command.getSecondPoint().getX() - command.getFirstPoint().getX())) + command.getFirstPoint().getX());
        double randomY = (int) ((Math.random() * (command.getSecondPoint().getY() - command.getFirstPoint().getY())) + command.getFirstPoint().getY());

        moveToSpecificDirection(new MoveCommand(normalizeCoords(new Coords(randomX, randomY)), command.getS()), dt);
    }

    @Override
    public void signal(SignalCommand command) {
        this.signal = command.getLabel();
    }

    @Override
    public void unSignal(UnSignalCommand command) {
        if (Objects.equals(this.signal, command.getLabel())) {
            this.signal = null;
        }
    }

    @Override
    public void follow(FollowCommand command, Space space, Double dt) {
        double maxX = coords.getX() + command.getDistance();
        double maxY = coords.getY() + command.getDistance();

        Robot followRobot = null;

        for (Robot robot : space.getRobots()) {
            if (followRobot == null) {
                if (robot.getCoords().getX() <= maxX && robot.getCoords().getY() <= maxY && robot != this) {
                    followRobot = robot;
                }
            } else if (robot.getCoords().getX() <= followRobot.getCoords().getX() || robot.getCoords().getY() <= followRobot.getCoords().getY()) {
                followRobot = robot;
            }
        }

        //If there is no robot ad the distance, robot move to the random direction between -distance and distance
        if (followRobot == null) {
            int randomX = (int) ((Math.random() * (command.getDistance() - (-command.getDistance()))) + (-command.getDistance()));
            int randomY = (int) ((Math.random() * (command.getDistance() - (-command.getDistance()))) + (-command.getDistance()));
        }

        //Robot move to the middle of the two robots
        if (followRobot != null) {
            double x = ((coords.getX() + followRobot.getCoords().getX()) / 2);
            double y = ((coords.getY() + followRobot.getCoords().getY()) / 2);

            moveToSpecificDirection(new MoveCommand(normalizeCoords(new Coords(x, y)), command.getS()), dt);
        }

    }

    @Override
    public void stop() {
        this.condition = Condition.STOP;
    }

    @Override
    public Coords getCoords() {
        return this.coords;
    }

    @Override
    public void executeCommand(double dt, Space space) {
        SampleCommand command = commandList.poll();
        if (command == null) {
            return;
        }

        if (command instanceof ContinueCommand) {
            if (lastCommand == null) {
                return;
            }

            command = lastCommand;
        } else this.lastCommand = command;

        commandSorter(command, space, dt);

    }

    /**
     * This method sort the command to the specific method
     *
     * @param command
     * @param space
     * @param dt
     */
    private void commandSorter (SampleCommand command, Space space, Double dt){
        switch (command) {
            case MoveRandomCommand cmd -> moveToRandomDirection(cmd, dt);
            case MoveCommand cmd -> moveToSpecificDirection(cmd, dt);
            case UnSignalCommand cmd -> unSignal(cmd);
            case SignalCommand cmd -> signal(cmd);
            case FollowCommand cmd -> follow(cmd, space, dt);
            case StopCommand cmd -> stop();
            default -> throw new IllegalStateException("Unexpected value: " + command);
        }
    }

    @Override
    public <T extends SampleCommand> void addCommands(T command) {
        commandList.add(command);
    }

    @Override
    public String getSignal() {
        return this.signal;
    }

    /**
     * This method returns the direction from the coordinates by normalizing them.
     *
     * @param coords
     * @return Direction direction
     */
    public Direction normalizeCoords(Coords coords) {
        double magnitude = Math.sqrt(Math.pow(coords.getX(), 2) + Math.pow(coords.getY(), 2));

        if (magnitude == 0) {
            throw new IllegalArgumentException("coords must be different from (0, 0)");
        }

        return new Direction((coords.getX() / magnitude), (coords.getY() / magnitude));
    }
}
