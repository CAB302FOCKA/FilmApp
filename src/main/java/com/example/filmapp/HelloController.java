package com.example.filmapp;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.util.List;

public class HelloController {

    public ScrollPane scrollPane;
    @FXML private GridPane movieContainer;

    @FXML
    public void initialize() {
        List<String> titles = List.of(
                "Breaking Bad",
                "Better Call Saul",
                "Game of Thrones",
                "Stranger Things",
                "The Office",
                "The Mandalorian",
                "Friends",
                "The Crown",
                "The Witcher",
                "The Boys",
                "The Walking Dead",
                "Ozark",
                "The Last of Us",
                "Peaky Blinders",
                "House of the Dragon",
                "Loki",
                "Black Mirror",
                "Westworld",
                "The Queen’s Gambit",
                "Mindhunter"
        );
        int column = 0;
        int row = 0;

        for (String title : titles) {
            try {
                TMDB series = new TMDB();
                series.getSeriesOverview(title);

                FXMLLoader loader = new FXMLLoader(getClass().getResource("movie_card.fxml"));
                Parent card = loader.load();

                movie_card_controller controller = loader.getController();
                controller.setData(series.title, series.overview, series.getImageUrl());

                movieContainer.add(card, column, row);

                column++;
                if (column == 3) {
                    column = 0;
                    row++;
                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
