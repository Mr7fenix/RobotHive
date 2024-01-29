package it.unicam.cs.pa.ma114110.command.iteration;

import it.unicam.cs.pa.ma114110.command.SampleCommand;
import it.unicam.cs.pa.ma114110.command.program.Program;

public class Until extends SampleCommand {
    private final String label;
    private final Program program;

    public Until(String label, Program program) {
        if (label == null) {
            throw new NullPointerException("Label cannot be null");
        }
        if (program == null) {
            throw new NullPointerException("Program cannot be null");
        }

        this.label = label;
        this.program = program;
    }

    public String getLabel() {
        return label;
    }

    public Program getProgram() {
        return program;
    }
}
