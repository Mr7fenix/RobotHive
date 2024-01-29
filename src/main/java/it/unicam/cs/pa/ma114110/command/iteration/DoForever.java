package it.unicam.cs.pa.ma114110.command.iteration;

import it.unicam.cs.pa.ma114110.command.SampleCommand;
import it.unicam.cs.pa.ma114110.command.program.Program;

public class DoForever extends SampleCommand {
    Program program;
    public DoForever(Program program) {
        if (program == null) {
            throw new NullPointerException("Program cannot be null");
        }

        this.program = program;
    }
}
