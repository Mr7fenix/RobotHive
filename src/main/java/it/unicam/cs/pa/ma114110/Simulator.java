package it.unicam.cs.pa.ma114110;

import it.unicam.cs.pa.ma114110.robot.Robot;
import it.unicam.cs.pa.ma114110.space.Space;

public class Simulator implements SimulatorInterface {
    private final Space space;

    public Simulator(Space space){
        if (space == null) throw new NullPointerException("space must be null");

        this.space = space;
    }

    @Override
    public void simulate(double dt, double time) {
        for (double x = dt; x <= time; x += dt){
            for (Robot robot : space.getRobots()){
                robot.executeNextCommand(dt);
            }
        }
    }
}
