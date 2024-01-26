package it.unicam.cs.pa.ma114110;

import it.unicam.cs.pa.ma114110.robot.Robot;
import it.unicam.cs.pa.ma114110.space.Space;

public class Simulator implements SimulatorInterface {
    private final Space space;
    private Double dt;
    private Double time;

    public Simulator(Space space){
        if (space == null) throw new NullPointerException("grid must be null");

        this.space = space;

    }

    @Override
    public void simulate(double dt, double time) {
        this.dt = dt;
        this.time = time;

        for (double x = dt; x <= time; x += dt){
            for (Robot robot : space.getRobots()){
                robot.executeCommand(dt, space);
            }
        }
    }
}
