package it.unicam.cs.pa.ma114110;

import it.unicam.cs.pa.ma114110.robot.Robot;
import it.unicam.cs.pa.ma114110.space.Environment;

public class Simulator implements SimulatorInterface {
    private final Environment environment;

    public Simulator(Environment environment){
        if (environment == null) throw new NullPointerException("space must be null");

        this.environment = environment;
    }

    @Override
    public void simulate(double dt, double time) {
        for (double x = dt; x <= time; x += dt){
            for (Robot robot : environment.getRobots()){
                robot.executeNextCommand(dt);
            }
        }
    }
}
