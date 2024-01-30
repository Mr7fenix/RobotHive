package it.unicam.cs.pa.ma114110.parser;

import it.unicam.cs.pa.ma114110.command.ContinueCommand;
import it.unicam.cs.pa.ma114110.command.move.FollowCommand;
import it.unicam.cs.pa.ma114110.command.move.MoveCommand;
import it.unicam.cs.pa.ma114110.command.move.MoveRandomCommand;
import it.unicam.cs.pa.ma114110.command.program.Program;
import it.unicam.cs.pa.ma114110.command.signal.SignalCommand;
import it.unicam.cs.pa.ma114110.command.signal.UnSignalCommand;
import it.unicam.cs.pa.ma114110.space.Environment;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommandParserTest {

    @Test
    void parse() {
        CommandParser parser = new CommandParser();
        Environment environment = new Environment();


        Program program = new Program(environment);
        program.setCommandList(parser.parse("../api/src/test/resources/parserTest/test_MOVE"));

        assertInstanceOf(MoveCommand.class, program.getCommandList().getFirst());
        assertEquals(2, program.getCommandList().size());
        assertThrows(RuntimeException.class, () -> parser.parse("../api/src/test/resources/parserTest/test_MissingStop"));
    }

    @Test
    void parseMove() {
        CommandParser parser = new CommandParser();
        Environment environment = new Environment();

        Program program = new Program(environment);
        program.setCommandList(parser.parse("../api/src/test/resources/parserTest/test_MOVE"));

        assertInstanceOf(MoveCommand.class, program.getCommandList().getFirst());
    }
    @Test
    void parseMoveRandom() {
        CommandParser parser = new CommandParser();
        Environment environment = new Environment();

        Program program = new Program(environment);
        program.setCommandList(parser.parse("../api/src/test/resources/parserTest/test_MOVE_RANDOM"));

        assertInstanceOf(MoveRandomCommand.class, program.getCommandList().getFirst());
    }
    @Test
    void parseSignal() {
        CommandParser parser = new CommandParser();
        Environment environment = new Environment();

        Program program = new Program(environment);
        program.setCommandList(parser.parse("../api/src/test/resources/parserTest/test_SIGNAL"));

        assertInstanceOf(SignalCommand.class, program.getCommandList().getFirst());
    }
    @Test
    void parseUnSignal() {
        CommandParser parser = new CommandParser();
        Environment environment = new Environment();

        Program program = new Program(environment);
        program.setCommandList(parser.parse("../api/src/test/resources/parserTest/test_UNSIGNAL"));

        assertInstanceOf(UnSignalCommand.class, program.getCommandList().getFirst());
    }
    @Test
    void parseFollow() {
        CommandParser parser = new CommandParser();
        Environment environment = new Environment();

        Program program = new Program(environment);
        program.setCommandList(parser.parse("../api/src/test/resources/parserTest/test_FOLLOW"));

        assertInstanceOf(FollowCommand.class, program.getCommandList().getFirst());
    }

    @Test
    void parseContinue() {
        CommandParser parser = new CommandParser();
        Environment environment = new Environment();

        Program program = new Program(environment);
        program.setCommandList(parser.parse("../api/src/test/resources/parserTest/test_CONTINUE"));

        assertInstanceOf(ContinueCommand.class, program.getCommandList().getFirst());
    }
}