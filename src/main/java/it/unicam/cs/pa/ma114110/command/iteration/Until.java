package it.unicam.cs.pa.ma114110.command.iteration;

import it.unicam.cs.pa.ma114110.command.SampleCommand;
import it.unicam.cs.pa.ma114110.command.program.Program;

public class Until extends IterationCommand {
    private final String label;

    public Until(String label, Program program) {
        super(program);

        if (label == null) {
            throw new NullPointerException("Label cannot be null");
        }

        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
