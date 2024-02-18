package it.unicam.cs.pa.ma114110;

import it.unicam.cs.pa.ma114110.robot.Robot;
import it.unicam.cs.pa.ma114110.space.Environment;

import java.util.ArrayList;
import java.util.List;

public class Simulator implements SimulatorInterface {
    private final Environment environment;

    public Simulator(EnvironmentInterface environment) {
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
    public void simulateStepByStep(double dt, double time, double currentTime) {
        for (Robot robot : environment.getRobots()) {
            robot.executeNextCommand(dt);
        }
    }
}
