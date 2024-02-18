package it.unicam.cs.pa.ma114110.controller;

import it.unicam.cs.pa.ma114110.RobotHiveGUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;

public class MainMenuController {
    public Button selectFile;

    @FXML
    public void selectFile(ActionEvent actionEvent) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select a Environment File");

        File file = fileChooser.showOpenDialog(RobotHiveGUI.getInstance().getStage());
        if (file != null) {
            RobotHiveGUI.getInstance().setUpScene("gameScene.fxml", file);
        }
    }
}
