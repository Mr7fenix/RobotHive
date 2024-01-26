package com.mr7fenix;

/*
 * RobotHive: a game about robots
 *
 * Author: Mr7Fenix
 * License: MIT License
 */

import com.mr7fenix.command.Command;

import java.util.List;

public interface RobotInterface {

    /**
     * This method is used to check the surroundings of the robot and return a list of 8 adjacent cells
     * @return the list of 8 adjacent cells of the robot
     */
    List<Cell> getAdiacentCells();

    /**
     * This metod is used to check the condition of robot, Condition cen be "stop" or "move"
     * @return the condition of the robot
     */
    Condition getCondition();

    /**
     * This method is used to move robot in specific direction when cell is clear
     * @param direction the direction where the robot should move
     */
    void moveToSpecificDirection(String direction);

    /**
     * This method is used to move robot in specific cell when cell is clear
     * @param cell the cell where the robot should move
     */
    void moveToSpecificCell(Cell cell);

    /**
     * This method is used to move robot in the same direction of adjacent robot
     * @param robot the robot where the robot should move
     */
    void moveToSpecificRobot(Robot robot);

    /**
     * This method is used to stop the robot. this method change condition of robot to "stop"
     */
    void stop();


    /**
     * This method is used to get the coords of the robot
     * @return the coords of the robot
     */
    Coords getCoords();

}
