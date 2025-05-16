package com.example.filmapp.controller;

import com.example.filmapp.model.Media;
import com.example.filmapp.factory.MediaFactory;
import com.example.filmapp.service.API;
import com.example.filmapp.service.DatabaseConnection;
import com.example.filmapp.state.AppState;
import com.example.filmapp.util.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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

        loadWatchlist();
        loadForYou();    // Random IDs
        loadTrending();  // From API
    }

    private void loadWatchlist() {
        String userId = AppState.getInstance().getCurrentUserId();

        String query = "SELECT mediaType, mediaID FROM watchlist WHERE userID = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String mediaType = rs.getString("mediaType");
                String mediaId = rs.getString("mediaID");
                System.out.println("📺 Found watchlist item: " + mediaType + " - " + mediaId);
                addMediaCard(mediaType, mediaId, watchlistBox);
            }

        } catch (SQLException e) {
            System.err.println("Failed to load watchlist for user " + userId);
            e.printStackTrace();
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
                    trendingBox.getChildren().add(buildCard(media, false));
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
                HBox card = buildCard(media, targetBox == watchlistBox); // show remove only on watchlist
                targetBox.getChildren().add(card);
            }
        } catch (Exception e) {
            System.err.println("Failed to load: " + id);
        }
    }


    private HBox buildCard(Media media, boolean includeRemoveButton) {
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

        if (includeRemoveButton) {
            javafx.scene.control.Button removeButton = new javafx.scene.control.Button("Remove");
            removeButton.setOnAction(e -> {
                removeFromWatchlist(media.getId(), media.getMediaType());
                watchlistBox.getChildren().remove(card);
            });
            infoBox.getChildren().add(removeButton);
        }

        return card;
    }


    private void removeFromWatchlist(String mediaId, String mediaType) {
        String userId = AppState.getInstance().getCurrentUserId();

        String sql = "DELETE FROM watchlist WHERE userID = ? AND mediaID = ? AND mediaType = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, userId);
            stmt.setString(2, mediaId);
            stmt.setString(3, mediaType);

            int rows = stmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Removed from watchlist: " + mediaId);
            }

        } catch (SQLException e) {
            System.err.println("Could not remove from watchlist");
            e.printStackTrace();
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
    @FXML
    private void handleAccountButton() {
        try {
            SceneManager.switchTo("account_settings.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
