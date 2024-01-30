package it.unicam.cs.pa.ma114110.command.iteration;

import it.unicam.cs.pa.ma114110.command.SampleCommand;
import it.unicam.cs.pa.ma114110.command.program.Program;

import java.util.LinkedList;

public class RepeatCommand extends IterationCommand {
    private final int times;

    public RepeatCommand(int times, LinkedList<SampleCommand> commandList) {
        super(commandList);
        if (times < 0) {
            throw new IllegalArgumentException("Times cannot be negative");
        }
        this.times = times;
    }

    public int getTimes() {
        return times;
    }
}
