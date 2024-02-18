package it.unicam.cs.pa.ma114110.space;

import it.unicam.cs.pa.ma114110.area.Area;
import it.unicam.cs.pa.ma114110.robot.Robot;

import java.util.List;

/**
 * This interface is used to represent a grid of all cells
 * grid contains all cells and particular areas when specific effects
 * <p>
 * his responsibility is to provide a list of all cells and list of all aras
 */
public interface EnvironmentInterface {
    /**
     * This method is used to add a robot to the space
     * @param robot robot to add
     */
    void addRobot(Robot robot);

    /**
     * This method is used to remove a robot from the space
     * @param robot robot to remove
     */
    void removeRobot(Robot robot);

    /**
     * This method is used to add area in the space
     * @param area area to add
     */
    void addArea(Area area);

    /**
     * This method is used to generate random robots in random coords on the space
     */
    void genRobots();

    /**
     * This method is used to get all robots in the space
     * @return list of all robots
     */
    List<Robot> getRobots();

    /**
     * This method is used to get a robot by coords
     * @param coords coords of the robot
     * @return robot or null if not found
     */
    Robot getRobotByCoords(Coords coords);

    /**
     * This method is used to get all areas in the space
     * @return list of all areas
     */
    List<Area> getAreas();

    /**
     * This method is used to get robot by id
     * @return robot whit corresponding id
     */
    Robot getRobotById(int id);
}
