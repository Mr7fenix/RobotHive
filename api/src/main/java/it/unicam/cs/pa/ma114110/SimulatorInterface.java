package it.unicam.cs.pa.ma114110;

import java.util.List;

public interface SimulatorInterface {
    /**
     * This method simulates the environment for the given time.
     * @param dt the time step
     * @param time the time to simulate
     */
    void simulate(double dt, double time);

    /**
     * This method simulates the environment for the given time.
     * @param dt the time step
     * @param time the time to simulate
     * @param currentTime the current time of the simulation
     */
    double simulateStepByStep(double dt, double time, double currentTime);
}
