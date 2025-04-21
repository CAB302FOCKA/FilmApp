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
// Owen R is here - Test
// Fin H test
public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1920, 1080);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();

        API api = new API();
        Media media = MediaFactory.fromJson(api.getMediaDetails("tv", "95396"), "tv");

        System.out.println(media.id);
        System.out.println(media.title);
        System.out.println(media.rating);
        System.out.println(media.overview);
        System.out.println(media.posterPath);
    }

    public static void main(String[] args) {
        launch();
    }
}