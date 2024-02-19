package it.unicam.cs.pa.ma114110.parser;

import it.unicam.cs.pa.ma114110.command.ContinueCommand;
import it.unicam.cs.pa.ma114110.command.SampleCommand;
import it.unicam.cs.pa.ma114110.command.StopCommand;
import it.unicam.cs.pa.ma114110.command.iteration.ForeverCommand;
import it.unicam.cs.pa.ma114110.command.iteration.RepeatCommand;
import it.unicam.cs.pa.ma114110.command.iteration.UntilCommand;
import it.unicam.cs.pa.ma114110.command.move.FollowCommand;
import it.unicam.cs.pa.ma114110.command.move.MoveCommand;
import it.unicam.cs.pa.ma114110.command.move.MoveRandomCommand;
import it.unicam.cs.pa.ma114110.command.signal.SignalingCommand;
import it.unicam.cs.pa.ma114110.space.coords.SampleCoords;
import it.unicam.cs.pa.ma114110.space.direction.SampleDirection;

import java.io.File;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Scanner;

public class CommandParser implements Parser<LinkedList<Command>> {

    /**
     * Parse a program from a file
     *
     * @param path the path of the file to parse
     * @return the program
     */
    public LinkedList<Command> parse(String path) {
        return parse(new File(path));
    }

    @Override
    public LinkedList<Command> parse(File file) {
        try {
            Scanner scanner = new Scanner(file);

            LinkedList<SampleCommand> commands = new LinkedList<>();

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();

                if (isIterationCommand(line)) {
                    commands.add(parseIterationCommand(line, scanner));

                } else commands.add(parseSampleCommand(line));

                if (!scanner.hasNextLine() && !line.equals("STOP")) {
                    throw new RuntimeException("Program not closed");
                }
            }


            scanner.close();
            return commands;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * This method is used to check if command is an iteration command
     *
     * @param line the line to check
     * @return the program
     */
    private boolean isIterationCommand(String line) {
        String[] tokens = line.split(" ");
        return switch (tokens[0]) {
            case "REPEAT", "UNTIL", "DO" -> true;
            default -> false;
        };
    }

    /**
     * This method is used to parse an iteration command
     *
     * @param line    the line to parse
     * @param scanner used for get the iteration program
     * @return the program
     */
    private Command parseIterationCommand(String line, Scanner scanner) {
        LinkedList<Command> commands = parseIterationProgram(scanner);

        String[] tokens = line.split(" ");
        return switch (tokens[0]) {
            case "REPEAT" -> parseRepeatCommand(tokens, commands);
            case "UNTIL" -> parseUntilCommand(tokens, commands);
            case "DO" -> parseDoCommand(tokens, commands);
            default -> throw new RuntimeException(STR."\{line} is not a valid iteration command");
        };
    }

    /**
     * This method is used to parse a DO command
     *
     * @param tokens   the tokens of the command
     * @param commands list of commands to repeat
     * @return the program
     */
    private Command parseDoCommand(String[] tokens, LinkedList<Command> commands) {
        if (tokens.length != 2) {
            throw new RuntimeException(STR."\{Arrays.toString(tokens)} is not a valid DO FOREVER command");
        }

        return new ForeverCommand(commands);
    }

    /**
     * This method is used to parse a REPEAT command
     *
     * @param tokens   the tokens of the command
     * @param commands list of commands to repeat
     * @return the program
     */
    private Command parseRepeatCommand(String[] tokens, LinkedList<Command> commands) {
        if (tokens.length != 2) {
            throw new RuntimeException(STR."\{Arrays.toString(tokens)} is not a valid REPEAT command");
        }

        return new RepeatCommand(
                Integer.parseInt(tokens[1]),
                commands
        );
    }

    /**
     * This method is used to parse a UNTIL command
     *
     * @param tokens   the tokens of the command
     * @param commands list of commands to repeat
     * @return the program
     */
    private Command parseUntilCommand(String[] tokens, LinkedList<Command> commands) {
        if (tokens.length != 2) {
            throw new RuntimeException(STR."\{Arrays.toString(tokens)} is not a valid UNTIL command");
        }

        return new UntilCommand(
                tokens[1],
                commands
        );
    }

    /**
     * This method is used to get command to repeat in iteration
     *
     * @param scanner used for get the command to repeat in iteration
     * @return the list of commands to repeat
     */
    private LinkedList<SampleCommand> parseIterationProgram(Scanner scanner) {
        LinkedList<SampleCommand> commands = new LinkedList<>();

        if (scanner.hasNextLine()) {
            String nextLine = scanner.nextLine();
            while (!nextLine.equals("DONE")) {
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

    /**
     * This method is used to parse a non iteration command
     *
     * @param line the line to parse
     * @return the command
     */
    private SampleCommand parseSampleCommand(String line) {
        String[] tokens = line.split(" ");

        return switch (tokens[0]) {
            case "MOVE" -> parseMoveCommand(tokens);
            case "FOLLOW" -> parseFollowCommand(tokens);
            case "SIGNAL", "UNSIGNAL" -> parseSignalingCommand(tokens);
            case "CONTINUE" -> parseContinueCommand(tokens);
            case "STOP" -> new StopCommand();
            default -> throw new RuntimeException(STR."\{line} is not a valid command");
        };
    }

    /**
     * This method is used to parse a MOVE command
     *
     * @param tokens the tokens of the command
     * @return the command
     */
    private SampleCommand parseMoveCommand(String[] tokens) {
        if (Objects.equals(tokens[1], "RANDOM")) {
            return parseMoveCommandRandom(tokens);
        }


        if (tokens.length != 4) {
            throw new RuntimeException(STR."\{Arrays.toString(tokens)} is not a valid MOVE command");
        }

        return new MoveCommand(
                new SampleDirection(Double.parseDouble(tokens[1]), Double.parseDouble(tokens[2])),
                Double.parseDouble(tokens[3])
        );
    }

    /**
     * This method is used to parse a MOVE RANDOM command
     *
     * @param tokens the tokens of the command
     * @return the command
     */
    private SampleCommand parseMoveCommandRandom(String[] tokens) {
        if (tokens.length != 7) {
            throw new RuntimeException(STR."\{Arrays.toString(tokens)} is not a valid MOVE command");
        }

        return new MoveRandomCommand(
                new SampleCoords(Double.parseDouble(tokens[2]), Double.parseDouble(tokens[3])),
                new SampleCoords(Double.parseDouble(tokens[4]), Double.parseDouble(tokens[5])),
                Double.parseDouble(tokens[6]));
    }

    /**
     * This method is used to parse a FOLLOW command
     *
     * @param tokens the tokens of the command
     * @return the command
     */
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

    /**
     * This method is used to parse a SIGNAL command
     *
     * @param tokens the tokens of the command
     * @return the command
     */
    private SampleCommand parseSignalCommand(String[] tokens) {
        if (tokens.length != 2) {
            throw new RuntimeException(STR."\{Arrays.toString(tokens)} is not a valid SIGNAL command");
        }

        if (tokens[0].equals("SIGNAL")){
            return new SignalingCommand(
                    tokens[1],
                    true
            );
        }else {
            return new SignalingCommand(
                    tokens[1],
                    false
            );
        }
    }

    /**
     * This method is used to parse a UNSIGNAL command
     *
     * @param tokens the tokens of the command
     * @return the command
     */
    private SampleCommand parseUnsignalCommand(String[] tokens) {
        if (tokens.length != 2) {
            throw new RuntimeException(STR."\{Arrays.toString(tokens)} is not a valid UNSIGNAL command");
        }

        return new UnSignalCommand(
                tokens[1]
        );
    }

    private SampleCommand parseContinueCommand(String[] tokens) {
        if (tokens.length != 2) {
            throw new RuntimeException(STR."\{Arrays.toString(tokens)} is not a valid CONTINUE command");
        }

        return new ContinueCommand(
                Integer.parseInt(tokens[1])
        );
    }
}
