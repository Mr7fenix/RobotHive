package it.unicam.cs.pa.ma114110.controller;

import it.unicam.cs.pa.ma114110.RobotHiveGUI;
import it.unicam.cs.pa.ma114110.simulator.SampleSimulator;
import it.unicam.cs.pa.ma114110.area.SampleArea;
import it.unicam.cs.pa.ma114110.area.CircularSampleArea;
import it.unicam.cs.pa.ma114110.area.RectangleSampleArea;
import it.unicam.cs.pa.ma114110.command.Command;
import it.unicam.cs.pa.ma114110.command.program.Program;
import it.unicam.cs.pa.ma114110.parser.CommandParser;
import it.unicam.cs.pa.ma114110.robot.SampleRobot;
import it.unicam.cs.pa.ma114110.space.coords.SampleCoords;
import it.unicam.cs.pa.ma114110.space.coords.Coords;
import it.unicam.cs.pa.ma114110.space.enviroment.SampleEnvironment;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.util.Duration;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class GameController implements DataController<SampleEnvironment> {
    public SampleEnvironment environment;

    public SampleSimulator simulator;
    public TextArea textArea;
    public Button playButton;
    public TextField addX;
    public TextField addY;
    public TextField textDt;
    public TextField textTime;
    private Circle selectedRobot;

    public Button genRobot;
    public Group uiEnvironment;
    public VBox programLoader;
    public TextField conditionText;
    public TextField labelText;
    public TextField xText;
    public TextField yText;

    private double currentTime = 0;

    private final HashMap<Integer, Circle> circlesMap = new HashMap<>();

    public void setData(SampleEnvironment environment) {
        this.environment = environment;
        this.simulator = new SampleSimulator(environment);

        spawnArea();
    }

    public void genRobots(ActionEvent actionEvent) {
        environment.genRobots();
        spawnRobots();
    }

    private void spawnRobots() {
        for (SampleRobot robot : environment.getRobots()) {
            if (!circlesMap.containsKey(robot.getId())) {
                Circle circle = new Circle(robot.getCoords().x(), robot.getCoords().y(), 10, Color.BLACK);

                circle.setOnMouseClicked(this::robotSelected);
                circlesMap.put(robot.getId(), circle);

                uiEnvironment.getChildren().add(circle);
            }
        }
    }

    private void robotSelected(MouseEvent event) {
        if (selectedRobot != null) {
            selectedRobot.setFill(Color.BLACK);
            textArea.clear();
        }
        this.selectedRobot = (Circle) event.getSource();
        this.selectedRobot.setFill(Color.rgb(163, 224, 124, 1));


        setRobotField(getSelectedRobotId());
    }

    private void setRobotField(int id) {
        programLoader.setDisable(false);

        conditionText.setText(environment.getRobotById(id).getCondition().toString());
        labelText.setText(environment.getRobotById(id).getSignal());

        Coords coords = environment.getRobotById(id).getCoords();
        xText.setText(String.valueOf(coords.x()));
        yText.setText(String.valueOf(coords.y()));
    }

    @FXML
    private void chooseProgramToLoad(ActionEvent actionEvent) throws FileNotFoundException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select a Environment File");

        File file = fileChooser.showOpenDialog(RobotHiveGUI.getInstance().getStage());
        if (file != null) {
            setTextArea(file);
            parseProgram(file);
        }
    }

    private void setProgramToRobot(LinkedList<Command> commands) {
        Program program = new Program(environment);
        program.setCommandList(commands);

        environment.getRobotById(getSelectedRobotId()).addProgram(program);
    }

    private int getSelectedRobotId() {
        for (Map.Entry<Integer, Circle> entry : circlesMap.entrySet()) {
            if (entry.getValue().equals(selectedRobot)) {
                return entry.getKey();
            }
        }
        throw new RuntimeException("Selected robot doesn't exist");
    }

    private void parseProgram(File file) {
        CommandParser commandParser = new CommandParser();
        commandParser.parse(file);

        setProgramToRobot(commandParser.parse(file));
    }

    private void setTextArea(File file) throws FileNotFoundException {
        try {
            Scanner scanner = new Scanner(file);
            StringBuilder stringBuilder = new StringBuilder();
            while (scanner.hasNextLine()) {
                stringBuilder.append(scanner.nextLine()).append("\n");
            }
            textArea.setText(stringBuilder.toString());
            scanner.close();

        } catch (Exception e) {
            throw new FileNotFoundException("File not found");
        }
    }


    private void spawnArea() {
        for (SampleArea sampleArea : environment.getAreas()) {
            switch (sampleArea) {
                case RectangleSampleArea rectangleArea -> spawnRectangleArea(rectangleArea);
                case CircularSampleArea circularArea -> spawnCircularArea(circularArea);
                default -> throw new IllegalStateException(STR."Unexpected value: \{sampleArea}");
            }
        }
    }

    private void spawnRectangleArea(RectangleSampleArea area) {
        double x = area.getStart().x();
        double y = area.getStart().y();
        double width = area.getWidth();
        double height = area.getHeight();

        Rectangle rectangle = new Rectangle(x, y, width, height);
        rectangle.setFill(Color.BLUE);
        rectangle.setOpacity(0.5);

        uiEnvironment.getChildren().add(rectangle);
    }

    private void spawnCircularArea(CircularSampleArea area) {
        double x = area.getCenter().x();
        double y = area.getCenter().y();
        double radius = area.getRadius();

        Circle circle = new Circle(x, y, radius);
        circle.setFill(Color.RED);
        circle.setOpacity(0.5);

        uiEnvironment.getChildren().add(circle);
    }

    public void play(ActionEvent actionEvent) {
        boolean isStepByStep = !actionEvent.getSource().equals(playButton);

        if (isDouble(textDt.getText()) && isDouble(textTime.getText())) {
            timeCorrect();
            Thread simulatorThread = new Thread(() -> playThread(isStepByStep));
            simulatorThread.start();

            Thread uiThread = new Thread(() -> {
                try {
                    uiUpdate(simulatorThread);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
            uiThread.start();
        } else {
            timeError();
        }

    }

    private void playThread(boolean isStepByStep) {
        playButton.setDisable(true);
        textDt.setDisable(true);
        textTime.setDisable(true);

        if (isStepByStep) {
            currentTime = simulator.simulateStepByStep(Double.parseDouble(textDt.getText()), Double.parseDouble(textTime.getText()), currentTime);
        } else {
            simulator.simulate(Double.parseDouble(textDt.getText()), Double.parseDouble(textTime.getText()));
        }


        playButton.setDisable(false);
        textDt.setDisable(false);
        textTime.setDisable(false);
        textDt.clear();
    }

    private void uiUpdate(Thread simulatorThread) throws InterruptedException {
        while (simulatorThread.isAlive()) {
            for (SampleRobot robot : environment.getRobots()) {
                Circle circle = circlesMap.get(robot.getId());

                TranslateTransition translateTransition = new TranslateTransition();
                translateTransition.setNode(circle);
                translateTransition.setDuration(Duration.millis(100));

                translateTransition.setToX(robot.getCoords().x() - circle.getCenterX());
                translateTransition.setToY(robot.getCoords().y() - circle.getCenterY());
                translateTransition.play();

                robotDataUpdate();
            }
        }
    }

    private void robotDataUpdate() {
        int id = getSelectedRobotId();
        Coords coords = environment.getRobotById(id).getCoords();
        xText.setText(String.valueOf(coords.x()));
        yText.setText(String.valueOf(coords.y()));
        labelText.setText(environment.getRobotById(id).getSignal());
        conditionText.setText(environment.getRobotById(id).getCondition().toString());
    }

    public void addRobot(ActionEvent actionEvent) {
        if (addX.getText().isEmpty() || addY.getText().isEmpty()) {
            addX.setBorder(Border.stroke(Color.RED));
            addY.setBorder(Border.stroke(Color.RED));
        } else {
            if (isDouble(addX.getText()) && isDouble(addY.getText())) {
                environment.addRobot(new SampleRobot(new SampleCoords(Double.parseDouble(addX.getText()), Double.parseDouble(addY.getText())), environment.getNewId()));
                spawnRobots();
            }

            addX.clear();
            addY.clear();
        }
    }

    private boolean isDouble(String s) {
        return s.matches("^[0-9]+(\\.[0-9]+)?$");
    }

    private void timeError() {
        textDt.setBorder(Border.stroke(Color.RED));
        textTime.setBorder(Border.stroke(Color.RED));
    }

    private void timeCorrect() {
        textDt.setBorder(Border.EMPTY);
        textTime.setBorder(Border.EMPTY);
    }
}
