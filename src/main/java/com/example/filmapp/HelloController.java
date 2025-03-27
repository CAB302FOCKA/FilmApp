package com.example.filmapp;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.IOException;

public class HelloController {
    @FXML
    private Label welcomeText;
    @FXML
    private Label seriesTitle;
    @FXML
    private Label airDate;

    private TMDB series;

    @FXML
    public void initialize() throws IOException {
        series = new TMDB();
        series.getSeriesOverview("Better Call Saul");

        System.out.println(series.id);

        welcomeText.setText(series.overview);
        seriesTitle.setText(series.title);
        airDate.setText(" (" + series.yearAired + ")");
    }
}