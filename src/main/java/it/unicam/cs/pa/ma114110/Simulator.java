package it.unicam.cs.pa.ma114110;

import java.util.Timer;

public class Simulate implements Simulator {
    public Double dt;
    public Double time;

    @Override
    public void simulate(double dt, double time) {
        this.dt = dt;
        this.time = time;


        for (double x = 1; x <= time; x += dt){
            
        }
    }
}
