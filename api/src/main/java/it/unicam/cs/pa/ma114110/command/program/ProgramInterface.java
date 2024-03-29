package it.unicam.cs.pa.ma114110.command.program;

import it.unicam.cs.pa.ma114110.command.Command;
import it.unicam.cs.pa.ma114110.space.enviroment.Environment;

import java.util.Deque;

public interface ProgramInterface {
    Deque<Command> getCommandList();

    /**
     * This method returns the last command of the program and removes it from the program
     *
     * @return the last command of the program, return null if the program is empty
     */
    Command getNextCommand();


    /**
     * This method returns the last used command of the program but it doesn't remove it from the program
     *
     * @return the last command of the program
     */
    Command getCommand();

    /**
     * This method adds a command to the program
     *
     * @param command the command to add
     */
    <T extends Command> void addCommand(T command);


    /**
     * This method removes the last command of the program
     */
    void removeLastCommand();

    /**
     * This method clears the program
     */
    void clearCommandList();

    /**
     * This method sets the last command of the program
     *
     * @param command the command to set
     */
    <T extends SampleCommand> void setLastCommand(T command);

    /**
     * This method sets the command list of the program
     *
     * @param commandList the command list to set
     */
    void setCommandList(Deque<Command> commandList);

    /**
     * This method returns the size of the program
     *
     * @return the size of the program
     */
    int size();

    /**
     * This method returns the space when program is executed
     *
     * @return the space of the program is executed
     */
    Environment getSpace();

    /**
     * this method add command at the first position of the Deque
     * @param command command to add on the Deque
     */
    void addFirst(Command command);
}
