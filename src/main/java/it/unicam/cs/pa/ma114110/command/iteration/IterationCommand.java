package it.unicam.cs.pa.ma114110.command.iteration;

import it.unicam.cs.pa.ma114110.command.SampleCommand;
import it.unicam.cs.pa.ma114110.command.program.Program;

public abstract class IterationCommand extends SampleCommand {
    private final Program program;


    public IterationCommand (Program program){
        if (program == null) {
            throw new NullPointerException("Program cannot be null");
        }

        this.program = program;
    }

    public Program getProgram() {
        return program;
    }
}
