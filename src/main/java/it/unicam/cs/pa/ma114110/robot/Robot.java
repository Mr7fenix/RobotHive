package com.mr7fenix;

import com.mr7fenix.command.Command;

import java.util.List;

public class Robot implements RobotInterface {
    private Coords coords;
    private Condition condition;

    public Robot(Coords coords) {
        this.condition = Condition.STOP;

        if (coords == null) {
            throw new IllegalArgumentException("Coords cannot be null");
        }

        this.coords = coords;

    }


    @Override
    public List<Cell> getAdiacentCells() {
        return null;
    }

    @Override
    public Condition getCondition() {
        return this.condition;
    }

    @Override
    public void moveToSpecificDirection(String direction) {
    }

    @Override
    public void moveToSpecificCell(Cell cell) {

    }

    @Override
    public void moveToSpecificRobot(Robot robot) {

    }

    @Override
    public void stop() {
        this.condition = Condition.STOP;
    }

    @Override
    public Coords getCoords() {
        return this.coords;
    }
}
