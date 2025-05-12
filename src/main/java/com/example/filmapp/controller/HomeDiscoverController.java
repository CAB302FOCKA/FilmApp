package com.example.filmapp.controller;

import com.example.filmapp.model.Media;
import com.example.filmapp.factory.MediaFactory;
import com.example.filmapp.service.API;
import com.example.filmapp.util.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class HomeDiscoverController {

    @FXML
    private VBox watchlistBox;

    @FXML
    private VBox forYouBox;

    @FXML
    private VBox trendingBox;

    @FXML
    public void initialize() {
        System.out.println("HomeDiscoverController initialized");

        loadWatchlist(); // Hardcoded
        loadForYou();    // Random IDs
        loadTrending();  // From API
    }

    private void loadWatchlist() {
        Object[][] watchlist = {
                {"tv", "1396"},     // Breaking Bad
                {"tv", "1398"},     // Game of Thrones
                {"tv", "1668"},     // Friends
        };
        for (Object[] entry : watchlist) {
            addMediaCard((String) entry[0], (String) entry[1], watchlistBox);
        }
    }

    private void loadForYou() {
        Object[][] suggestions = {
                {"movie", "603"},    // The Matrix
                {"tv", "4613"},      // The Office
                {"tv", "61889"},     // The Mandalorian
        };
        for (Object[] entry : suggestions) {
            addMediaCard((String) entry[0], (String) entry[1], forYouBox);
        }
    }

    private void loadTrending() {
        try {
            API api = new API();
            JSONArray results = api.getTrendingMediaList("movie");
            if (results == null) return;

            for (int i = 0; i < Math.min(5, results.size()); i++) {
                JSONObject json = (JSONObject) results.get(i);
                Media media = MediaFactory.fromJson(json, "movie");
                if (media != null && media.getPosterPath() != null) {
                    trendingBox.getChildren().add(buildCard(media));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addMediaCard(String type, String id, VBox targetBox) {
        try {
            API api = new API();
            JSONObject json = api.getMediaDetails(type, id);
            Media media = MediaFactory.fromJson(json, type);
            if (media != null && media.getPosterPath() != null) {
                targetBox.getChildren().add(buildCard(media));
            }
        } catch (Exception e) {
            System.err.println("Failed to load: " + id);
        }
    }

    private HBox buildCard(Media media) {
        ImageView poster = new ImageView();
        Image image = new Image("https://image.tmdb.org/t/p/w200" + media.getPosterPath(), true);
        poster.setImage(image);
        poster.setFitWidth(80);
        poster.setFitHeight(120);

        Label title = new Label(media.getTitle());
        title.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");

        Label desc = new Label(media.getOverview());
        desc.setWrapText(true);
        desc.setMaxWidth(300);

        VBox infoBox = new VBox(title, desc);
        infoBox.setSpacing(5);

        HBox card = new HBox(poster, infoBox);
        card.setSpacing(15);
        return card;
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
    @FXML
    private void handleAccountButton() {
        try {
            SceneManager.switchTo("settings.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
