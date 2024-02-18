package it.unicam.cs.pa.ma114110.robot;

/*
 * RobotHive: a game about robots
 *
 * Author: Mr7Fenix
 * License: MIT License
 */

import it.unicam.cs.pa.ma114110.command.program.Program;
import it.unicam.cs.pa.ma114110.space.Coords;

public interface RobotInterface {
    /**
     * This metod is used to check the condition of robot, Condition cen be "stop" or "move"
     *
     * @return the condition of the robot
     */
    Condition getCondition();

    /**
     * This method is used to get the coords of the robot
     *
     * @return the coords of the robot
     */
    CoordsInterface getCoords();

    /**
     * This executes the command in stack
     *
     * @param dt    the time to execute the command
     */
    void executeNextCommand(double dt);

    /**
     * This method is used to set program to the robot
     *
     * @param program the program to set
     */
    void addProgram(Program program);

    /**
     * This method is used to get signal of the robot
     *
     * @return signal of the robot
     */
    String getSignal();

    /**
     * This method is used to get Program of the robot
     * @return program of the robot
     */
    ProgramInterface getProgram();

    /**
     * This method is used to get the id of the robot
     * @return id of the robot
     */
    int getId();
}
