package it.unicam.cs.pa.ma114110.space;

import it.unicam.cs.pa.ma114110.area.Area;
import it.unicam.cs.pa.ma114110.robot.Robot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * This class is used to represent the grid of all cells
 * grid contains all cells and particular areas when specific effects
 * <p>
 * his responsibility is to provide a list of all cells and list of all aras
 * <p>
 * the used method to populate the grid is defined lazy loading, when is needed a not existing cell,
 * the grid create a new cell and add it to the list of cells. In this method is reduced the memory usage
 */
public class Environment implements EnvironmentInterface {
    List<Robot> robotList = new ArrayList<Robot>();
    List<Area> areaList = new ArrayList<Area>();

    @Override
    public void addRobot(Robot robot) {
        if (robot == null) {
            throw new IllegalArgumentException("Robot cannot be null");
        }
        this.robotList.add(robot);
    }

    @Override
    public void removeRobot(Robot robot) {
        if (robot == null) {
            throw new IllegalArgumentException("Robot cannot be null");
        }
        this.robotList.remove(robot);
    }

    @Override
    public void addArea(Area area) {
        if (area == null) {
            throw new IllegalArgumentException("Area cannot be null");
        }
        this.areaList.add(area);
    }

    @Override
    public void genRobots() {
        int range = 1600 - 5 + 1;
        for (int i = 0; i < 10; i++) {
            double x = (Math.random() * range) + 5;
            double y = (Math.random() * range) + 5;
            this.addRobot(new Robot(new Coords(x, y), getNewId()));
        }
    }

    @Override
    public List<Robot> getRobots() {
        return this.robotList;
    }

    @Override
    public Robot getRobotByCoords(Coords coords) {
        if (coords == null) {
            throw new IllegalArgumentException("Coords cannot be null");
        }

        for (Robot robot : robotList) {
            if (Objects.equals(robot.getCoords().x(), coords.x()) && Objects.equals(robot.getCoords().y(), coords.y())) {
                return robot;
            }
        }
        return null;
    }

    @Override
    public List<Area> getAreas() {
        return this.areaList;
    }

    @Override
    public Robot getRobotById(int id) {
        for (Robot robot : robotList) {
            if (robot.getId() == id) {
                return robot;
            }
        }
        return null;
    }

    /**
     * This method is used to get a new id for a robot
     * @return new id
     */
    public int getNewId() {
        return robotList.size() + 1;
    }
}
