package it.unicam.cs.pa.ma114110.parser;

import it.unicam.cs.pa.ma114110.command.ContinueCommand;
import it.unicam.cs.pa.ma114110.command.move.FollowCommand;
import it.unicam.cs.pa.ma114110.command.move.MoveCommand;
import it.unicam.cs.pa.ma114110.command.move.MoveRandomCommand;
import it.unicam.cs.pa.ma114110.command.program.Program;
import it.unicam.cs.pa.ma114110.command.signal.SignalingCommand;
import it.unicam.cs.pa.ma114110.space.enviroment.SampleEnvironment;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommandParserTest {

    @Test
    void parse() {
        CommandParser parser = new CommandParser();
        SampleEnvironment environment = new SampleEnvironment();


        Program program = new Program(environment);
        program.setCommandList(parser.parse("../api/src/test/resources/parserTest/test_MOVE"));

        assertInstanceOf(MoveCommand.class, program.getCommandList().getFirst());
        assertEquals(2, program.getCommandList().size());
        assertThrows(RuntimeException.class, () -> parser.parse("../api/src/test/resources/parserTest/test_MissingStop"));
    }

    @Test
    void parseMove() {
        CommandParser parser = new CommandParser();
        SampleEnvironment environment = new SampleEnvironment();

        Program program = new Program(environment);
        program.setCommandList(parser.parse("../api/src/test/resources/parserTest/test_MOVE"));

        assertInstanceOf(MoveCommand.class, program.getCommandList().getFirst());
    }
    @Test
    void parseMoveRandom() {
        CommandParser parser = new CommandParser();
        SampleEnvironment environment = new SampleEnvironment();

        Program program = new Program(environment);
        program.setCommandList(parser.parse("../api/src/test/resources/parserTest/test_MOVE_RANDOM"));

        assertInstanceOf(MoveRandomCommand.class, program.getCommandList().getFirst());
    }
    @Test
    void parseSignal() {
        CommandParser parser = new CommandParser();
        SampleEnvironment environment = new SampleEnvironment();

        Program program = new Program(environment);
        program.setCommandList(parser.parse("../api/src/test/resources/parserTest/test_SIGNAL"));

        assertInstanceOf(SignalingCommand.class, program.getCommandList().getFirst());
    }
    @Test
    void parseUnSignal() {
        CommandParser parser = new CommandParser();
        SampleEnvironment environment = new SampleEnvironment();

        Program program = new Program(environment);
        program.setCommandList(parser.parse("../api/src/test/resources/parserTest/test_UNSIGNAL"));

        assertInstanceOf(SignalingCommand.class, program.getCommandList().getFirst());
    }
    @Test
    void parseFollow() {
        CommandParser parser = new CommandParser();
        SampleEnvironment environment = new SampleEnvironment();

        Program program = new Program(environment);
        program.setCommandList(parser.parse("../api/src/test/resources/parserTest/test_FOLLOW"));

        assertInstanceOf(FollowCommand.class, program.getCommandList().getFirst());
    }

    @Test
    void parseContinue() {
        CommandParser parser = new CommandParser();
        SampleEnvironment environment = new SampleEnvironment();

        Program program = new Program(environment);
        program.setCommandList(parser.parse("../api/src/test/resources/parserTest/test_CONTINUE"));

        assertInstanceOf(ContinueCommand.class, program.getCommandList().getFirst());
    }

    @Test
    void parseRepeat() {
        CommandParser parser = new CommandParser();
        SampleEnvironment environment = new SampleEnvironment();

        Program program = new Program(environment);
        program.setCommandList(parser.parse("../api/src/test/resources/parserTest/test_REPEAT"));
        assertThrows(RuntimeException.class, () -> parser.parse("../api/src/test/resources/parserTest/test_REAPEAT_MissingDone"));

        assertEquals(2, program.getCommandList().size());
    }

    @Test
    void parseUntil() {
        CommandParser parser = new CommandParser();
        SampleEnvironment environment = new SampleEnvironment();

        Program program = new Program(environment);
        program.setCommandList(parser.parse("../api/src/test/resources/parserTest/test_UNTIL"));

        assertThrows(RuntimeException.class, () -> parser.parse("../api/src/test/resources/parserTest/test_UNTIL_MissingDone"));
        assertEquals(2, program.getCommandList().size());
    }

    @Test
    void parseDoForever() {
        CommandParser parser = new CommandParser();
        SampleEnvironment environment = new SampleEnvironment();

        Program program = new Program(environment);
        program.setCommandList(parser.parse("../api/src/test/resources/parserTest/test_FOREVER"));

        assertThrows(RuntimeException.class, () -> parser.parse("../api/src/test/resources/parserTest/test_FOREVER_MissingDone"));
        assertEquals(2, program.getCommandList().size());
    }
}