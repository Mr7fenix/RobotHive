package it.unicam.cs.pa.ma114110.parser;

import it.unicam.cs.pa.ma114110.area.Area;
import it.unicam.cs.pa.ma114110.area.CircularArea;
import it.unicam.cs.pa.ma114110.area.RectangleArea;
import it.unicam.cs.pa.ma114110.space.Coords;
import it.unicam.cs.pa.ma114110.space.Environment;

import java.io.File;
import java.util.Scanner;

public class EnvironmentParser implements ParserInterface<Environment> {
    public Environment parse(String path) {
            return parse(new File(path));
    }

    public Environment parse(File file) {
        try {
            Scanner scanner = new Scanner(file);
            Environment environment = new Environment();

            while (scanner.hasNextLine()){
                environment.addArea(parseArea(scanner.nextLine()));
            }

            scanner.close();
            return environment;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Parse a line of the file and return the corresponding area
     * @param line the line to parse
     * @return the parsed area
     */
    private Area parseArea(String line){
        String[] tokens = line.split(" ");
        return switch (tokens[1]){
            case "RECTANGLE" -> parseRectangle(tokens);
            case "CIRCLE" -> parseCircle(tokens);
            default -> throw new RuntimeException("Invalid area type");
        };
    }

    /**
     * Parse a line of the file and return the corresponding rectangle area
     * @param tokens the tokens of the line
     * @return the parsed rectangle area
     */
    private Area parseRectangle(String[] tokens) {
        if (tokens.length != 6) {
            throw new RuntimeException("Invalid number of arguments");
        }

        return new RectangleArea(
                tokens[0],
                new Coords(Double.parseDouble(tokens[2]), Double.parseDouble(tokens[3])),
                Double.parseDouble(tokens[4]),
                Double.parseDouble(tokens[5])
        );
    }

    /**
     * Parse a line of the file and return the corresponding circular area
     * @param tokens the tokens of the line
     * @return the parsed circular area
     */
    private Area parseCircle(String[] tokens) {
        if (tokens.length != 5) {
            throw new RuntimeException("Invalid number of arguments");
        }

        return new CircularArea(
                tokens[0],
                new Coords(Double.parseDouble(tokens[2]), Double.parseDouble(tokens[3])),
                Double.parseDouble(tokens[4])
        );
    }
}
