package it.unicam.cs.pa.ma114110.parser;

import it.unicam.cs.pa.ma114110.area.Area;
import it.unicam.cs.pa.ma114110.area.CircularArea;
import it.unicam.cs.pa.ma114110.area.RectangleArea;
import it.unicam.cs.pa.ma114110.space.Coords;
import it.unicam.cs.pa.ma114110.space.Environment;

import java.io.File;
import java.util.Scanner;

public class EnvironmentParser extends Parser {
    public Environment parse(String path) {
        try {
            File file = new File(path);
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

    private Area parseArea(String line){
        String[] tokens = line.split(" ");
        return switch (tokens[1]){
            case "RECTANGLE" -> parseRectangle(tokens);
            case "CIRCLE" -> parseCircle(tokens);
            default -> throw new RuntimeException("Invalid area type");
        };
    }

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
