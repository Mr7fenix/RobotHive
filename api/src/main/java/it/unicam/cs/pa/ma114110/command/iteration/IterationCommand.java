package it.unicam.cs.pa.ma114110.command.iteration;

import it.unicam.cs.pa.ma114110.command.SampleCommand;

import java.util.LinkedList;

public abstract class IterationCommand implements CommandInterface {
    private final LinkedList<CommandInterface> commandList;


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
