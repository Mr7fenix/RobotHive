package it.unicam.cs.pa.ma114110.robot;

/*
 * RobotHive: a game about robots
 *
 * Author: Mr7Fenix
 * License: MIT License
 */

import it.unicam.cs.pa.ma114110.command.SampleCommand;
import it.unicam.cs.pa.ma114110.command.move.FollowCommand;
import it.unicam.cs.pa.ma114110.command.signal.SignalCommand;
import it.unicam.cs.pa.ma114110.command.move.MoveCommand;
import it.unicam.cs.pa.ma114110.command.move.MoveRandomCommand;
import it.unicam.cs.pa.ma114110.command.signal.UnSignalCommand;
import it.unicam.cs.pa.ma114110.space.Coords;
import it.unicam.cs.pa.ma114110.space.Space;

import java.util.List;

public interface RobotInterface {
    /**
     * This metod is used to check the condition of robot, Condition cen be "stop" or "move"
     *
     * @return the condition of the robot
     */
    Condition getCondition();

    /**
     * This method is used to move robot in specific direction
     *
     * @param command the command to move the robot, it contains the direction and the speed
     * @param dt      the time to move the robot
     */
    void moveToSpecificDirection(MoveCommand command, Double dt);

    /**
     * This method is used to move robot in random direction.
     *
     * @param command the command to move the robot, it contains the speed and rang of direction
     * @param dt      the time to move the robot
     */
    void moveToRandomDirection(MoveRandomCommand command, Double dt);

    /**
     * This method is used to report the robot label
     *
     * @param command the command to report the robot, it contains the label
     */
    void signal(SignalCommand command);

    /**
     * This method is used to unreport the robot label
     *
     * @param command the command to unreport the robot, it contains the label to unreport
     */
    void unSignal(UnSignalCommand command);

    /**
     * This method is used to follow the robot to specific label and distance
     *
     * @param command the command to follow the robot, it contains the label to follow and the distance
     * @param space   the space where the robot is
     */
    void follow(FollowCommand command, Space space, Double dt);

    /**
     * This method is used to stop the robot. this method change condition of robot to "stop"
     */
    void stop();

    /**
     * This method is used to get the coords of the robot
     *
     * @return the coords of the robot
     */
    Coords getCoords();

    /**
     * This executes the command in stack
     *
     * @param dt    the time to execute the command
     * @param space the space where the robot is
     */
    void executeCommand(double dt, Space space);

    /**
     * Add command in stack
     *
     * @param command the command to add
     */
    <T extends SampleCommand> void addCommands(T command);

    /**
     * This method is used to get signal of the robot
     *
     * @return signal of the robot
     */
    String getSignal();

}
