package it.unicam.cs.pa.ma114110.command.iteration;

import it.unicam.cs.pa.ma114110.command.Command;

import java.util.LinkedList;

public abstract class IterationCommand implements Command {
    private final LinkedList<Command> commandList;


    public IterationCommand (LinkedList<Command> commandList){
        if (commandList == null) {
            throw new NullPointerException("Program cannot be null");
        }

        this.commandList = commandList;
    }

    public LinkedList<Command> getCommandList() {
        return commandList;
    }
}
