package com.example.filmapp.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class SceneManager {

    private static Stage stage;
    private static boolean testMode = false;

    public static void enableTestMode() {
        testMode = true;
    }

    public static void initialize(Stage primaryStage) {
        stage = primaryStage;

        if (!testMode) {
            //Set startup size to work on most devices
            stage.setWidth(1280);
            stage.setHeight(720);
            stage.setTitle("Focka Films");
        }
    }

    public static void switchTo(String fxml) throws IOException {
        URL url = SceneManager.class.getResource("/com/example/filmapp/" + fxml);

        if (url == null)
            throw new RuntimeException("FXML file not found: " + fxml);

        if (testMode) {
            System.out.println("[TEST MODE] Would switch to: " + fxml);
            return;
        }

        FXMLLoader fxmlLoader = new FXMLLoader(url);

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
