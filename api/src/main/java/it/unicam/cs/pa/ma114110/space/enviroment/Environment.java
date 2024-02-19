package it.unicam.cs.pa.ma114110.space.enviroment;

import it.unicam.cs.pa.ma114110.area.SampleArea;
import it.unicam.cs.pa.ma114110.robot.SampleRobot;
import it.unicam.cs.pa.ma114110.space.coords.SampleCoords;

import java.util.List;

/**
 * This interface is used to represent a grid of all cells
 * grid contains all cells and particular areas when specific effects
 * <p>
 * his responsibility is to provide a list of all cells and list of all aras
 */
public interface Environment {
    /**
     * This method is used to add a robot to the space
     * @param robot robot to add
     */
    void addRobot(SampleRobot robot);

    /**
     * This method is used to remove a robot from the space
     * @param robot robot to remove
     */
    void removeRobot(SampleRobot robot);

    /**
     * This method is used to add area in the space
     * @param sampleArea area to add
     */
    void addArea(SampleArea sampleArea);

    /**
     * This method is used to generate random robots in random coords on the space
     */
    void genRobots();

    /**
     * This method is used to get all robots in the space
     * @return list of all robots
     */
    List<SampleRobot> getRobots();

    /**
     * This method is used to get a robot by coords
     * @param coords coords of the robot
     * @return robot or null if not found
     */
    SampleRobot getRobotByCoords(SampleCoords coords);

    /**
     * This method is used to get all areas in the space
     * @return list of all areas
     */
    List<SampleArea> getAreas();

    /**
     * This method is used to get robot by id
     * @return robot whit corresponding id
     */
    SampleRobot getRobotById(int id);
}
