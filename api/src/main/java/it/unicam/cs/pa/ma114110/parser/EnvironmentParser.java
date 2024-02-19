package it.unicam.cs.pa.ma114110.parser;

import it.unicam.cs.pa.ma114110.area.SampleArea;
import it.unicam.cs.pa.ma114110.area.CircularSampleArea;
import it.unicam.cs.pa.ma114110.area.RectangleSampleArea;
import it.unicam.cs.pa.ma114110.space.coords.SampleCoords;
import it.unicam.cs.pa.ma114110.space.enviroment.SampleEnvironment;

import java.io.File;
import java.util.Scanner;

public class EnvironmentParser implements Parser<SampleEnvironment> {
    public SampleEnvironment parse(String path) {
            return parse(new File(path));
    }

    public SampleEnvironment parse(File file) {
        try {
            Scanner scanner = new Scanner(file);
            SampleEnvironment environment = new SampleEnvironment();

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
    private SampleArea parseArea(String line){
        String[] tokens = line.split(" ");
        return switch (tokens[1]){
            case "RECTANGLE" -> parseRectangle(tokens);
            case "CIRCLE" -> parseCircle(tokens);
            default -> throw new RuntimeException(STR."Invalid area type\{tokens[1]}");
        };
    }

    /**
     * Parse a line of the file and return the corresponding rectangle area
     * @param tokens the tokens of the line
     * @return the parsed rectangle area
     */
    private SampleArea parseRectangle(String[] tokens) {
        if (tokens.length != 6) {
            throw new RuntimeException("Invalid number of arguments");
        }

        return new RectangleSampleArea(
                tokens[0],
                new SampleCoords(Double.parseDouble(tokens[2]), Double.parseDouble(tokens[3])),
                Double.parseDouble(tokens[4]),
                Double.parseDouble(tokens[5])
        );
    }

    /**
     * Parse a line of the file and return the corresponding circular area
     * @param tokens the tokens of the line
     * @return the parsed circular area
     */
    private SampleArea parseCircle(String[] tokens) {
        if (tokens.length != 5) {
            throw new RuntimeException("Invalid number of arguments");
        }

        return new CircularSampleArea(
                tokens[0],
                new SampleCoords(Double.parseDouble(tokens[2]), Double.parseDouble(tokens[3])),
                Double.parseDouble(tokens[4])
        );
    }
}
