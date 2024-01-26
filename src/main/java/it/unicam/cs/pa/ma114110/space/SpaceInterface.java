package it.unicam.cs.pa.ma114110.space;

import it.unicam.cs.pa.ma114110.robot.Robot;
import it.unicam.cs.pa.ma114110.robot.RobotInterface;

import java.util.ArrayList;
import java.util.List;

/**
 * This interface is used to represent a grid of all cells
 * grid contains all cells and particular areas when specific effects
 * <p>
 * his responsibility is to provide a list of all cells and list of all aras
 */
public interface GridInterface {
    /**
     * @return list of all areas in the grid
     */
    List<Area> getAreas();

    /**
     * This method is used to add a new robot in the grid
     */
    void addRobot(Robot robot);

    List<Robot> getRobots();

    Robot getRobotByCoords(Coords coords);
}
