package com.example.filmapp;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.List;

public class HelloController {

    @FXML private VBox movieContainer;

    @FXML
    public void initialize() {
        List<String> titles = List.of("Better Call Saul", "Breaking Bad", "The Office", "Game of Thrones");

        for (String title : titles) {
            try {
                TMDB series = new TMDB();
                series.getSeriesOverview(title);

                FXMLLoader loader = new FXMLLoader(getClass().getResource("movie_card.fxml"));
                Parent card = loader.load();

                movie_card_controller controller = loader.getController();
                controller.setData(series.title, series.overview, series.getImageUrl());

                movieContainer.getChildren().add(card);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
