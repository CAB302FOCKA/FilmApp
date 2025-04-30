package com.example.filmapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

// Charlie Nielsen - Test push
// Kyha A. | git good
// Andrew Fernando - Andrew was here
// Jackson McGrath - Test Comment.
public class Home_Discover extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Home_Discover.class.getResource("home_discover.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("FOCKA Films");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}