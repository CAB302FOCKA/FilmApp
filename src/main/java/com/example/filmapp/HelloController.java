package com.example.filmapp;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;
import java.io.IOException;

public class HelloController {

    @FXML private VBox movieContainer;

    @FXML
    public void initialize() throws IOException {
        TMDB series = new TMDB();
        series.getSeriesOverview("Better Call Saul");

        // Load MovieCard.fxml
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/filmapp/MovieCard.fxml"));
        Parent card = loader.load();

        // Set the data
        movie_card_controller controller = loader.getController();
        controller.setData(series.title, series.overview, series.getImageUrl());

        // Add card to the container
        movieContainer.getChildren().add(card);
    }
}
