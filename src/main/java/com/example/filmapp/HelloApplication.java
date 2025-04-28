package com.example.filmapp;

import javafx.application.Application;
import javafx.stage.Stage;


//Main method to run the whole application
public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        SceneManager.setStage(stage);
        SceneManager.switchTo("login.fxml");
    }

    public static void main(String[] args) {
        launch();
    }
}
