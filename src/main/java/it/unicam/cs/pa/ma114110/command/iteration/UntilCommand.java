package it.unicam.cs.pa.ma114110.command.iteration;

import it.unicam.cs.pa.ma114110.command.SampleCommand;
import it.unicam.cs.pa.ma114110.command.program.Program;

import java.util.LinkedList;

public class UntilCommand extends IterationCommand {
    private final String label;

    public UntilCommand(String label, LinkedList<SampleCommand> commandList) {
        super(commandList);

        if (label == null) {
            throw new NullPointerException("Label cannot be null");
        }

        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
