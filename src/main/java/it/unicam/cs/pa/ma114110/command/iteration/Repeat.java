package it.unicam.cs.pa.ma114110.command.iteration;

import it.unicam.cs.pa.ma114110.command.SampleCommand;
import it.unicam.cs.pa.ma114110.command.program.Program;

public class Repeat extends SampleCommand {
    private final int times;
    private final Program program;

    public Repeat(int times, Program program) {
        if (times < 0) {
            throw new IllegalArgumentException("Times cannot be negative");
        }
        if (program == null) {
            throw new NullPointerException("Command cannot be null");
        }

        this.times = times;
        this.program = program;
    }

    public Program getCommand() {
        return program;
    }

    public int getTimes() {
        return times;
    }
}
