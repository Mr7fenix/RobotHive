package it.unicam.cs.pa.ma114110.space.enviroment;

import it.unicam.cs.pa.ma114110.area.SampleArea;
import it.unicam.cs.pa.ma114110.robot.SampleRobot;
import it.unicam.cs.pa.ma114110.space.coords.SampleCoords;

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
public class SampleEnvironment implements Environment {
    List<SampleRobot> robotList = new ArrayList<SampleRobot>();
    List<SampleArea> sampleAreaList = new ArrayList<SampleArea>();

    @Override
    public void addRobot(SampleRobot robot) {
        if (robot == null) {
            throw new IllegalArgumentException("Robot cannot be null");
        }
        this.robotList.add(robot);
    }

    @Override
    public void removeRobot(SampleRobot robot) {
        if (robot == null) {
            throw new IllegalArgumentException("Robot cannot be null");
        }
        this.robotList.remove(robot);
    }

    @Override
    public void addArea(SampleArea sampleArea) {
        if (sampleArea == null) {
            throw new IllegalArgumentException("Area cannot be null");
        }
        this.sampleAreaList.add(sampleArea);
    }

    @Override
    public void genRobots() {
        int range = 1600 - 5 + 1;
        for (int i = 0; i < 10; i++) {
            double x = (Math.random() * range) + 5;
            double y = (Math.random() * range) + 5;
            this.addRobot(new SampleRobot(new SampleCoords(x, y), getNewId()));
        }
    }

    @Override
    public List<SampleRobot> getRobots() {
        return this.robotList;
    }

    @Override
    public SampleRobot getRobotByCoords(SampleCoords coords) {
        if (coords == null) {
            throw new IllegalArgumentException("Coords cannot be null");
        }

        for (SampleRobot robot : robotList) {
            if (Objects.equals(robot.getCoords().x(), coords.x()) && Objects.equals(robot.getCoords().y(), coords.y())) {
                return robot;
            }
        }
        return null;
    }

    @Override
    public List<SampleArea> getAreas() {
        return this.sampleAreaList;
    }

    @Override
    public SampleRobot getRobotById(int id) {
        for (SampleRobot robot : robotList) {
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
