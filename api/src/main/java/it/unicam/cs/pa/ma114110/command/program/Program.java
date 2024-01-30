package it.unicam.cs.pa.ma114110.command.program;

import it.unicam.cs.pa.ma114110.command.SampleCommand;
import it.unicam.cs.pa.ma114110.space.Environment;

import java.util.LinkedList;

public class Program implements ProgramInterface {
    private LinkedList<SampleCommand> commandList = new LinkedList<>();
    private final Environment environment;

    public Program(Environment environment) {
        if (environment == null) {
            throw new NullPointerException("Space cannot be null");
        }
        this.environment = environment;
    }

    @Override
    public LinkedList<SampleCommand> getCommandList() {
        return commandList;
    }

    @Override
    public SampleCommand getNextCommand() {
        return commandList.poll();
    }

    @Override
    public SampleCommand getCommand() {
        return commandList.peek();
    }

    @Override
    public <T extends SampleCommand> void addCommand(T command) {
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
    public void setLastCommand(SampleCommand command) {
        if (command == null) {
            throw new NullPointerException("Command cannot be null");
        }

        commandList.set(commandList.size() - 1, command);
    }

    public void addFirst(SampleCommand command) {
        if (command == null) {
            throw new NullPointerException("Command cannot be null");
        }

        commandList.addFirst(command);
    }


    @Override
    public void setCommandList(LinkedList<SampleCommand> commandList) {
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