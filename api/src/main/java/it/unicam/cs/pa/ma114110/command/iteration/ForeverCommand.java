package it.unicam.cs.pa.ma114110.command.iteration;

import it.unicam.cs.pa.ma114110.command.SampleCommand;
import it.unicam.cs.pa.ma114110.command.program.Program;

import java.util.LinkedList;

public class ForeverCommand extends IterationCommand {
    public ForeverCommand(LinkedList<SampleCommand> commandList) {
        super(commandList);
    }
}
