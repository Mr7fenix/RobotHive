package it.unicam.cs.pa.ma114110;

import it.unicam.cs.pa.ma114110.controller.DataController;
import it.unicam.cs.pa.ma114110.parser.EnvironmentParser;
import it.unicam.cs.pa.ma114110.space.Environment;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class RobotHiveGUI extends Application {
    private Stage stage;
    private static RobotHiveGUI instance;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        this.instance = this;
        this.stage = stage;

        stage.setTitle("Robot Hive");
        stage.getIcons().add(new Image(getClass().getClassLoader().getResourceAsStream("icon/appIcon.png")));

        setUpScene();
        stage.show();
    }

    private void setUpScene() throws IOException {
            Scene mainMenu = new Scene(FXMLLoader.load(getClass().getClassLoader().getResource("mainMenuScene.fxml")));
            mainMenu.getStylesheets().add(String.valueOf(getClass().getClassLoader().getResource("style/app.css")));


            stage.setScene(mainMenu);
    }

    public void setUpScene(String path, File file) throws IOException {
        if (file == null) {
            throw new IOException("File not found");
        }

        Scene scene = new Scene(setDataToController(path, file));
        scene.getStylesheets().add(String.valueOf(getClass().getClassLoader().getResource("style/app.css")));
        stage.setScene(scene);
    }

    public  <C extends DataController<Environment>> Parent setDataToController(String path, File file) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource(path));
        Parent root  = loader.load();
        C controller = loader.getController();
        controller.setData(parseFile(file));

        return root;
    }

    private Environment parseFile(File file) {
        EnvironmentParser parser = new EnvironmentParser();
        return parser.parse(file);
    }

    public static RobotHiveGUI getInstance() {
        return instance;
    }

    public Stage getStage() {
        return stage;
    }
}