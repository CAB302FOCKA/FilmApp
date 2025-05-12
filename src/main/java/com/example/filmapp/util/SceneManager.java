package com.example.filmapp.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneManager {

    private static Stage stage;

    public static void initialize(Stage primaryStage) {
        stage = primaryStage;
        //Set startup size to work on most devices
        stage.setWidth(1280);
        stage.setHeight(720);
    }

    public static void switchTo(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SceneManager.class.getResource("/com/example/filmapp/" + fxml));
        Parent root = fxmlLoader.load();

        Scene currentScene = stage.getScene();
        boolean isFullScreen = stage.isFullScreen();
        double currentWidth = stage.getWidth();
        double currentHeight = stage.getHeight();

        Scene newScene = new Scene(root);

        if (isFullScreen) {
            stage.setFullScreen(true);
        } else {
            stage.setWidth(currentWidth);
            stage.setHeight(currentHeight);
        }

        stage.setScene(newScene);
    }
}
