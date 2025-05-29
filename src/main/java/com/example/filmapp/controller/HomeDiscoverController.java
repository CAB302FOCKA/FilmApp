package com.example.filmapp.controller;

import com.example.filmapp.model.Media;
import com.example.filmapp.factory.MediaFactory;
import com.example.filmapp.service.API;
import com.example.filmapp.service.DatabaseConnection;
import com.example.filmapp.state.AppState;
import com.example.filmapp.util.SceneManager;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.sql.*;

public class HomeDiscoverController {

    @FXML private VBox watchlistBox;
    @FXML private VBox forYouBox;
    @FXML private VBox trendingBox;

    @FXML
    public void initialize() {
        System.out.println("✅ HomeDiscoverController initialized");
        loadWatchlist();
        loadForYou();
        loadTrending();
    }

    private void loadWatchlist() {
        Task<Void> task = new Task<>() {
            @Override
            protected Void call() {
                String userId = AppState.getInstance().getCurrentUserId();
                String query = "SELECT mediaType, mediaID FROM watchlist WHERE userID = ?";

                try (Connection conn = DatabaseConnection.getConnection();
                     PreparedStatement stmt = conn.prepareStatement(query)) {

                    stmt.setString(1, userId);
                    ResultSet rs = stmt.executeQuery();

                    while (rs.next()) {
                        String mediaType = rs.getString("mediaType");
                        String mediaId = rs.getString("mediaID");
                        System.out.println("📺 Watchlist item: " + mediaType + " - " + mediaId);

                        JSONObject json = new API().getMediaDetails(mediaType, mediaId);
                        Media media = MediaFactory.fromJson(json, mediaType);
                        if (media != null && media.getPosterPath() != null) {
                            HBox card = buildCard(media, true);
                            Platform.runLater(() -> watchlistBox.getChildren().add(card));
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        };
        new Thread(task).start();
    }

    private void loadForYou() {
        Task<Void> task = new Task<>() {
            @Override
            protected Void call() {
                String userId = AppState.getInstance().getCurrentUserId();
                String query = "SELECT mediaType, mediaID FROM watchlist WHERE userID = ?";

                try (Connection conn = DatabaseConnection.getConnection();
                     PreparedStatement stmt = conn.prepareStatement(query)) {

                    stmt.setString(1, userId);
                    ResultSet rs = stmt.executeQuery();

                    if (!rs.isBeforeFirst()) {
                        System.out.println("📭 Watchlist empty, using trending for ForYou");
                        JSONArray results = new API().getTrendingMediaList("movie");
                        for (int i = 0; i < Math.min(5, results.size()); i++) {
                            JSONObject json = (JSONObject) results.get(i);
                            Media media = MediaFactory.fromJson(json, "movie");
                            if (media != null && media.getPosterPath() != null) {
                                HBox card = buildCard(media, false);
                                Platform.runLater(() -> forYouBox.getChildren().add(card));
                            }
                        }
                        return null;
                    }

                    int j = 0;
                    API api = new API();
                    while (rs.next() && j < 5) {
                        String mediaType = rs.getString("mediaType");
                        String mediaId = rs.getString("mediaID");

                        JSONObject details = api.getMediaDetails(mediaType, mediaId);
                        Media baseMedia = MediaFactory.fromJson(details, mediaType);
                        JSONArray recs = api.getRecommendationsList(baseMedia);
                        if (recs == null) continue;

                        for (int i = 0; i < Math.min(3, recs.size()); i++) {
                            JSONObject json = (JSONObject) recs.get(i);
                            Media media = MediaFactory.fromJson(json, mediaType);
                            if (media != null && media.getPosterPath() != null) {
                                HBox card = buildCard(media, false);
                                Platform.runLater(() -> forYouBox.getChildren().add(card));
                            }
                        }
                        j++;
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

                return null;
            }
        };
        new Thread(task).start();
    }

    private void loadTrending() {
        Task<Void> task = new Task<>() {
            @Override
            protected Void call() {
                try {
                    JSONArray results = new API().getTrendingMediaList("movie");
                    if (results == null) return null;

                    for (int i = 0; i < Math.min(5, results.size()); i++) {
                        JSONObject json = (JSONObject) results.get(i);
                        Media media = MediaFactory.fromJson(json, "movie");
                        if (media != null && media.getPosterPath() != null) {
                            HBox card = buildCard(media, false);
                            Platform.runLater(() -> trendingBox.getChildren().add(card));
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        };
        new Thread(task).start();
    }

    private HBox buildCard(Media media, boolean includeRemoveButton) {
        ImageView poster = new ImageView(new Image("https://image.tmdb.org/t/p/w200" + media.getPosterPath(), true));
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

        card.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            AppState.getInstance().setSelectedMedia(media);
            try {
                SceneManager.switchTo("TvMovieDetailsPage.fxml");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        if (includeRemoveButton) {
            Button removeButton = new Button("Remove");
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
                System.out.println("✅ Removed from watchlist: " + mediaId);
            }
        } catch (SQLException e) {
            System.err.println("❌ Could not remove from watchlist");
            e.printStackTrace();
        }
    }

    @FXML private void handleSearchButton() {
        try {
            SceneManager.switchTo("search.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML private void handleSettingsButton() {
        try {
            SceneManager.switchTo("settings.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML private void handleAccountButton() {
        try {
            SceneManager.switchTo("account_settings.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
