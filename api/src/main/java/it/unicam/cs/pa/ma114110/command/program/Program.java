package it.unicam.cs.pa.ma114110.command.program;

import it.unicam.cs.pa.ma114110.command.Command;
import it.unicam.cs.pa.ma114110.space.enviroment.Environment;

import java.util.LinkedList;

public class Program implements ProgramInterface {
    private Deque<Command> commandList = new LinkedList<>();
    private final Environment environment;

    public Program(Environment environment) {
        if (environment == null) {
            throw new NullPointerException("Space cannot be null");
        }
        this.environment = environment;
    }

    @Override
    public Deque<Command> getCommandList() {
        return commandList;
    }

    @Override
    public Command getNextCommand() {
        return commandList.poll();
    }

    @Override
    public Command getCommand() {
        return commandList.peek();
    }

    @Override
    public <T extends Command> void addCommand(T command) {
        if (command == null) {
            throw new NullPointerException("Command cannot be null");
        }

        this.commandList.add(command);
    }


    @Override
    public void removeLastCommand() {
        commandList.removeLast();
    }

    @Override
    public void clearCommandList() {
        commandList.clear();
    }

    @Override
    public void addFirst(Command command) {
        if (command == null) {
            throw new NullPointerException("Command cannot be null");
        }

        commandList.addFirst(command);
    }


    @Override
    public void setCommandList(Deque<Command> commandList) {
        if (commandList == null) {
            throw new NullPointerException("Command list cannot be null");
        }

        this.commandList = commandList;
    }

    @Override
    public int size() {
        return commandList.size();
    }

    @Override
    public Environment getSpace() {
        return this.environment;
    }
}
