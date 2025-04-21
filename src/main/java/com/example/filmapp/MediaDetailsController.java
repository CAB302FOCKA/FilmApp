package com.example.filmapp;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class MediaDetailsController {

    private final String movieTitle;
    private final double rating;
    private final String overview;
    private final String posterPath;

    public MediaDetailsController(String movieTitle, double rating, String overview, String posterPath){
        this.movieTitle = movieTitle;
        this.rating = Math.round(rating * 100.0) / 100.0; // round to 2 decmial places
        this.overview = overview;
        this.posterPath = posterPath;
    }

    @FXML
    private Label titleText;

    @FXML
    private Label overviewText;

    @FXML
    private Label ratingText;

    @FXML
    public void initialize(){
        titleText.setText(movieTitle);
        overviewText.setText(overview);
        ratingText.setText(String.format("%s/10",rating));
    }

    public void launch(Stage stage) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("TvMovieDetailsPage.fxml"));
        fxmlLoader.setController(this);

        Scene scene;
        try {
            scene = new Scene(fxmlLoader.load(), 600, 455);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        stage.setTitle("TV/Movie Details");
        stage.setScene(scene);
        stage.show();
    }
}
