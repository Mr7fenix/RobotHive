package it.unicam.cs.pa.ma114110.command.iteration;

import it.unicam.cs.pa.ma114110.command.Command;

import java.util.LinkedList;

public class ForeverCommand extends IterationCommand {
    public ForeverCommand(LinkedList<Command> commandList) {
        super(commandList);
    }
}
