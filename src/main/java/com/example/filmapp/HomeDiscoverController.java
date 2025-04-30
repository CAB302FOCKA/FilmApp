package com.example.filmapp;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.json.simple.JSONObject;

public class HomeDiscoverController {

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private GridPane movieContainer;

    @FXML
    public void initialize() {
        System.out.println("HomeDiscoverController initialized");

        loadMediaById();
    }

    private void loadMediaById() {
        Object[][] mediaList = {
                {"tv", "1396"},     // Breaking Bad
                {"tv", "1398"},     // Game of Thrones
                {"tv", "1668"},     // Friends
                {"tv", "4613"},     // The Office
                {"movie", "603"},   // The Matrix
                {"tv", "60574"},    // Peaky Blinders
                {"tv", "61889"},    // The Mandalorian
                {"tv", "71712"},    // The Good Doctor
                {"tv", "71912"}     // Example extra
        };

        int column = 0;
        int row = 0;

        for (Object[] entry : mediaList) {
            String type = (String) entry[0];
            String id = (String) entry[1];

            try {
                API api = new API();
                JSONObject json = api.getMediaDetails(type, id);
                Media media = MediaFactory.fromJson(json, type);

                if (media == null || media.posterPath == null) continue;

                ImageView poster = new ImageView("https://image.tmdb.org/t/p/w200" + media.posterPath);
                poster.setFitWidth(100);
                poster.setFitHeight(150);

                Label title = new Label(media.title);
                title.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");

                Label description = new Label(media.overview);
                description.setWrapText(true);
                description.setMaxWidth(250);

                VBox infoBox = new VBox(title, description);
                infoBox.setSpacing(5);

                HBox mediaCard = new HBox(poster, infoBox);
                mediaCard.setSpacing(20);

                movieContainer.add(mediaCard, column, row);

                column++;
                if (column == 3) {
                    column = 0;
                    row++;
                }

            } catch (Exception e) {
                System.err.println("Error loading media ID " + id + ": " + e.getMessage());
            }
        }
    }


    @FXML
    private void handleSearchButton() {
        try {
            SceneManager.switchTo("search.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleSettingsButton() {
        try {
            SceneManager.switchTo("settings.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
