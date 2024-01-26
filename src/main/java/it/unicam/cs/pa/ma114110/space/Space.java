package it.unicam.cs.pa.ma114110.space;

import it.unicam.cs.pa.ma114110.robot.Robot;
import it.unicam.cs.pa.ma114110.robot.RobotInterface;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used to represent the grid of all cells
 * grid contains all cells and particular areas when specific effects
 * <p>
 * his responsibility is to provide a list of all cells and list of all aras
 * <p>
 * the used method to populate the grid is defined lazy loading, when is needed a not existing cell,
 * the grid create a new cell and add it to the list of cells. In this method is reduced the memory usage
 */
public class Grid implements GridInterface {
    List<Robot> robotList;

    @Override
    public List<Area> getAreas() {
        return null;
    }

    @Override
    public void addRobot(Robot robot) {
        this.robotList.add(robot);
    }

    @Override
    public List<Robot> getRobots() {
        return this.robotList;
    }

    @Override
    public Robot getRobotByCoords(Coords coords) {
        for (Robot robot : robotList){
            if (robot.getCoords().equals(coords)){
                return robot;
            }
        }
        return null;
    }
}
