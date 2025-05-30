package com.example.filmapp;

import com.example.filmapp.util.SceneManager;
import javafx.application.Application;
import javafx.stage.Stage;

// Owen
//Main method to run the whole application
// Merge dev into master
public class App extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        SceneManager.initialize(stage);
        SceneManager.switchTo("login.fxml");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
