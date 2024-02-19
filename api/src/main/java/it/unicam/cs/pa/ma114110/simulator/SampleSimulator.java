package it.unicam.cs.pa.ma114110.simulator;

import it.unicam.cs.pa.ma114110.robot.SampleRobot;
import it.unicam.cs.pa.ma114110.space.enviroment.Environment;

public class SampleSimulator implements Simulator {
    private final Environment environment;

    public SampleSimulator(Environment environment) {
        if (environment == null) throw new NullPointerException("space must be null");

        this.environment = environment;
    }

    @Override
    public void simulate(double dt, double time) {
        for (double x = dt; x <= time; x += dt) {
            simulateStepByStep(dt, time, x);
        }
    }

    @Override
    public double simulateStepByStep(double dt, double time, double currentTime) {
        if (!(currentTime > time)){
            for (SampleRobot robot : environment.getRobots()) {
                robot.executeNextCommand(dt);
            }

            return currentTime + dt;
        }

        return 0;
    }
}
