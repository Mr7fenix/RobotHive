package it.unicam.cs.pa.ma114110.command.iteration;

import it.unicam.cs.pa.ma114110.command.SampleCommand;

import java.util.LinkedList;

public abstract class IterationCommand extends SampleCommand {
    private final LinkedList<SampleCommand> commandList;


    public IterationCommand (LinkedList<SampleCommand> commandList){
        if (commandList == null) {
            throw new NullPointerException("Program cannot be null");
        }

        this.commandList = commandList;
    }

    public LinkedList<SampleCommand> getCommandList() {
        return commandList;
    }
}
