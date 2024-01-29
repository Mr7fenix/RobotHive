package it.unicam.cs.pa.ma114110.command.iteration;

import it.unicam.cs.pa.ma114110.command.SampleCommand;
import it.unicam.cs.pa.ma114110.command.program.Program;

public class Repeat extends IterationCommand {
    private final int times;

    public Repeat(int times, Program program) {
        super(program);
        if (times < 0) {
            throw new IllegalArgumentException("Times cannot be negative");
        }


        this.times = times;
    }

    public int getTimes() {
        return times;
    }
}
