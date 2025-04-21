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
        API api = new API();
        Media media = MediaFactory.fromJson(api.getMediaDetails("tv", "95396"), "tv");

        System.out.println(media.id);
        System.out.println(media.title);
        System.out.println(media.rating);
        System.out.println(media.overview);
        System.out.println(media.posterPath);

        MediaDetailsController mediaDetailsController = new MediaDetailsController(media.title, media.rating, media.overview, media.posterPath);
        mediaDetailsController.launch(stage);
    }

    public static void main(String[] args) {
        launch();
    }
}