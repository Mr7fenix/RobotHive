package it.unicam.cs.pa.ma114110.parser;

import it.unicam.cs.pa.ma114110.command.SampleCommand;
import it.unicam.cs.pa.ma114110.command.StopCommand;
import it.unicam.cs.pa.ma114110.command.iteration.ForeverCommand;
import it.unicam.cs.pa.ma114110.command.iteration.RepeatCommand;
import it.unicam.cs.pa.ma114110.command.iteration.UntilCommand;
import it.unicam.cs.pa.ma114110.command.move.FollowCommand;
import it.unicam.cs.pa.ma114110.command.move.MoveCommand;
import it.unicam.cs.pa.ma114110.command.signal.SignalCommand;
import it.unicam.cs.pa.ma114110.command.signal.UnSignalCommand;
import it.unicam.cs.pa.ma114110.space.Direction;

import java.io.File;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Scanner;

public class CommandParser extends Parser {
    public CommandParser() {
    }

    public LinkedList<SampleCommand> parse(String path) {
        try {
            File file = new File(path);
            Scanner scanner = new Scanner(file);

            LinkedList<SampleCommand> commands = new LinkedList<>();

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();

                if (isIterationCommand(line)) {
                    commands.add(parseIterationCommand(line, scanner));

                } else commands.add(parseSampleCommand(line));
            }

            scanner.close();
            return commands;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    private boolean isIterationCommand(String line) {
        String[] tokens = line.split(" ");
        return switch (tokens[0]) {
            case "REPEAT", "UNTIL", "DO" -> true;
            default -> false;
        };
    }

    private SampleCommand parseIterationCommand(String line, Scanner scanner) {
        LinkedList<SampleCommand> commands = parseIterationProgram(scanner);

        String[] tokens = line.split(" ");
        return switch (tokens[0]) {
            case "REPEAT" -> parseRepeatCommand(tokens, commands);
            case "UNTIL" -> parseUntilCommand(tokens, commands);
            case "DO" -> new ForeverCommand(commands);
            default -> throw new RuntimeException(STR."\{line} is not a valid iteration command");
        };
    }

    private SampleCommand parseRepeatCommand(String[] tokens, LinkedList<SampleCommand> commands) {
        if (tokens.length != 2) {
            throw new RuntimeException(STR."\{Arrays.toString(tokens)} is not a valid REPEAT command");
        }

        return new RepeatCommand(
                Integer.parseInt(tokens[1]),
                commands
        );
    }

    private SampleCommand parseUntilCommand(String[] tokens, LinkedList<SampleCommand> commands) {
        if (tokens.length != 2) {
            throw new RuntimeException(STR."\{Arrays.toString(tokens)} is not a valid UNTIL command");
        }

        return new UntilCommand(
                tokens[1],
                commands
        );
    }

    private LinkedList<SampleCommand> parseIterationProgram(Scanner scanner) {
        LinkedList<SampleCommand> commands = new LinkedList<>();

        if (scanner.hasNextLine()) {
            String nextLine = scanner.nextLine();
            while (nextLine.equals("DONE")) {
                commands.add(parseSampleCommand(nextLine));
                if (scanner.hasNextLine()) {
                    nextLine = scanner.nextLine();
                } else {
                    throw new RuntimeException("Iteration command not closed");
                }
            }
        }
        return commands;
    }

    private SampleCommand parseSampleCommand(String line) {
        String[] tokens = line.split(" ");

        return switch (tokens[0]) {
            case "MOVE" -> parseMoveCommand(tokens);
            case "FOLLOW" -> parseFollowCommand(tokens);
            case "SIGNAL" -> parseSignalCommand(tokens);
            case "UNSIGNAL" -> parseUnsignalCommand(tokens);
            case "STOP" -> new StopCommand();
            default -> throw new RuntimeException(STR."\{line} is not a valid command");
        };
    }

    private SampleCommand parseMoveCommand(String[] tokens) {
        if (Objects.equals(tokens[1], "RANDOM")) {
            return parseMoveCommandRandom(tokens);
        }


        if (tokens.length != 4) {
            throw new RuntimeException(STR."\{Arrays.toString(tokens)} is not a valid MOVE command");
        }

        return new MoveCommand(
                new Direction(Double.parseDouble(tokens[1]), Double.parseDouble(tokens[2])),
                Double.parseDouble(tokens[3])
        );
    }

    private SampleCommand parseMoveCommandRandom(String[] tokens) {
        if (tokens.length != 3) {
            throw new RuntimeException(STR."\{Arrays.toString(tokens)} is not a valid MOVE command");
        }

        return new MoveCommand(
                new Direction(Double.parseDouble(tokens[1]), Double.parseDouble(tokens[2])),
                Math.random()
        );
    }

    private SampleCommand parseFollowCommand(String[] tokens) {
        if (tokens.length != 4) {
            throw new RuntimeException(STR."\{Arrays.toString(tokens)} is not a valid FOLLOW command");
        }

        return new FollowCommand(
                tokens[1],
                Double.parseDouble(tokens[2]),
                Double.parseDouble(tokens[3])
        );
    }

    private SampleCommand parseSignalCommand(String[] tokens) {
        if (tokens.length != 2) {
            throw new RuntimeException(STR."\{Arrays.toString(tokens)} is not a valid SIGNAL command");
        }

        return new SignalCommand(
                tokens[1]
        );
    }

    private SampleCommand parseUnsignalCommand(String[] tokens) {
        if (tokens.length != 2) {
            throw new RuntimeException(STR."\{Arrays.toString(tokens)} is not a valid UNSIGNAL command");
        }

        return new UnSignalCommand(
                tokens[1]
        );
    }
}
