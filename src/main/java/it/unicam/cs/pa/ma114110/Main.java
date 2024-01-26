package it.unicam.cs.pa.ma114110;

import it.unicam.cs.pa.ma114110.robot.Robot;
import it.unicam.cs.pa.ma114110.space.Coords;
import it.unicam.cs.pa.ma114110.space.Space;

public class Main {
    public static void main(String[] args) {
        Space space = new Space();

        space.addRobot(new Robot(new Coords(0, 0)));
        space.addRobot(new Robot(new Coords(-8, 8)));
        space.addRobot(new Robot(new Coords(7, 15)));

        Simulator simulator = new Simulator(space);
    }
}