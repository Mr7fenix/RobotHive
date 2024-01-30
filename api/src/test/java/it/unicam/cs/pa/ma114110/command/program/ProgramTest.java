package it.unicam.cs.pa.ma114110.command.program;

import it.unicam.cs.pa.ma114110.command.SampleCommand;
import it.unicam.cs.pa.ma114110.command.move.MoveCommand;
import it.unicam.cs.pa.ma114110.command.move.MoveRandomCommand;
import it.unicam.cs.pa.ma114110.space.Coords;
import it.unicam.cs.pa.ma114110.space.Direction;
import it.unicam.cs.pa.ma114110.space.Environment;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class ProgramTest {

    @Test
    void getCommandList() {
        Program program = new Program(new Environment());

        LinkedList<SampleCommand> commandList = new LinkedList<>();
        commandList.add(new MoveCommand(new Direction(1, 1), 1));
        program.setCommandList(commandList);
        assertEquals(commandList, program.getCommandList());
    }

    @Test
    void getNextCommand() {
        Program program = new Program(new Environment());
        assertNull(program.getNextCommand());

        MoveCommand command = new MoveCommand(new Direction(1, 1), 1);
        program.addCommand(command);
        assertEquals(command, program.getNextCommand());
        assertEquals(0, program.size());
    }

    @Test
    void getCommand() {
        Program program = new Program(new Environment());
        assertNull(program.getCommand());

        MoveCommand command = new MoveCommand(new Direction(1, 1), 1);
        program.addCommand(command);
        assertEquals(command, program.getCommand());
        assertEquals(1, program.size());
    }

    @Test
    void addCommand() {
        Program program = new Program(new Environment());
        assertThrows(NullPointerException.class, () -> program.addCommand(null));

        SampleCommand command = new MoveCommand(new Direction(1, 1), 1);
        program.addCommand(command);
        assertEquals(command, program.getNextCommand());
        assertEquals(0, program.size());
    }

    @Test
    void removeLastCommand() {
        Program program = new Program(new Environment());
        program.addCommand(new MoveCommand(new Direction(1, 1), 1));

        program.removeLastCommand();
        assertNull(program.getNextCommand());
    }

    @Test
    void clearCommandList() {
        Program program = new Program(new Environment());
        program.addCommand(new MoveCommand(new Direction(1, 1), 1));
        program.addCommand(new MoveCommand(new Direction(1, 1), 1));
        program.addCommand(new MoveCommand(new Direction(1, 1), 1));

        program.clearCommandList();
        assertNull(program.getNextCommand());
    }

    @Test
    void setLastCommand() {
        Program program = new Program(new Environment());
        MoveCommand command1 = new MoveCommand(new Direction(1, 1), 1);
        MoveRandomCommand command2 = new MoveRandomCommand(new Coords(1, 1), new Coords(1, 1), 2);

        program.addCommand(command1);
        assertThrows(NullPointerException.class, () -> program.setLastCommand(null));
        program.setLastCommand(command2);
        assertEquals(command2, program.getNextCommand());
    }

    @Test
    void setCommandList() {
        Program program = new Program(new Environment());
        LinkedList<SampleCommand> commandList = new LinkedList<>();
        commandList.add(new MoveCommand(new Direction(1, 1), 1));
        commandList.add(new MoveCommand(new Direction(1, 1), 1));
        commandList.add(new MoveCommand(new Direction(1, 1), 1));

        assertThrows(NullPointerException.class, () -> program.setCommandList(null));
        program.setCommandList(commandList);
        assertEquals(commandList, program.getCommandList());
    }

    @Test
    void size() {
        Program program = new Program(new Environment());
        assertEquals(0, program.size());

        program.addCommand(new MoveCommand(new Direction(1, 1), 1));
        assertEquals(1, program.size());
    }

    @Test
    void getSpace() {
        Environment environment = new Environment();
        Program program = new Program(environment);
        assertEquals(environment, program.getSpace());
    }
}