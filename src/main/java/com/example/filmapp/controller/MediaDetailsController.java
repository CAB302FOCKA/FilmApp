package com.example.filmapp.controller;

import com.example.filmapp.factory.MediaFactory;
import com.example.filmapp.model.Media;
import com.example.filmapp.model.Person;
import com.example.filmapp.service.API;
import com.example.filmapp.state.AppState;
import com.example.filmapp.util.SceneManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Label;
import javafx.scene.control.Button;

import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import com.example.filmapp.service.DatabaseConnection;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.net.URI;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;

public class MediaDetailsController {
    Media selectedMedia = AppState.getInstance().getSelectedMedia();

    @FXML
    private Label titleText;

    @FXML
    private Label overviewText;

    @FXML
    private Label ratingText;

    @FXML
    private ImageView posterImage;

    @FXML
    private FlowPane castContainer;

    @FXML
    private FlowPane similarToContainer;

    @FXML
    private FlowPane recommendationsContainer;

    @FXML
    private Label mediaLabel;

    @FXML
    private Button addToWatchlistButton;

    @FXML
    private Label trailerErrorLabel;

    @FXML
    private ImageView mediaBanner;

    @FXML
    private void handleAddToWatchlist() {
        String userId = AppState.getInstance().getCurrentUserId();
        String mediaId = selectedMedia.getId();
        String mediaType = selectedMedia.getMediaType();

        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "INSERT INTO watchlist (userID, mediaID, mediaType) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, userId);
            stmt.setString(2, mediaId);
            stmt.setString(3, mediaType);
            stmt.executeUpdate();
            System.out.println("Added to watchlist.");
        } catch (SQLException e) {
            System.out.println("Failed to add to watchlist.");
            e.printStackTrace();
        }
    }

    @FXML
    public void initialize(){
        titleText.setText(selectedMedia.getTitle());
        overviewText.setText(selectedMedia.getOverview());
        ratingText.setText(String.format("%s/10",selectedMedia.getRating()));
        posterImage.setImage(new Image("https://image.tmdb.org/t/p/w200" + selectedMedia.getPosterPath(), true));

        trailerErrorLabel.setText("");

        /// Transparent Media Banner for top of page
        /*
        mediaBanner.setImage(new Image("https://image.tmdb.org/t/p/w780" + selectedMedia.getBackdropPath(), true));
        mediaBanner.setFitWidth(1920);
        mediaBanner.setFitHeight(141);
        mediaBanner.setPreserveRatio(false);

        mediaBanner.setViewport(new Rectangle2D(mediaBanner.getX(), mediaBanner.getY(), 320, 289.5));
        */

        populateSimilarToContainer();
        populateRecommendationsContainer();
        populateCastContainer();
    }

    void populateCastContainer(){
        castContainer.getChildren().clear();

        try {
            API api = new API();
            JSONArray results = api.getCastList(selectedMedia);
            if (results == null) return;

            ArrayList<Person> castMembers = new ArrayList<>();

            for (Object person : results){
                JSONObject jsonPerson = (JSONObject) person;

                String actorName = (String) jsonPerson.get("name");
                String charName = (String) jsonPerson.get("character");
                String profilePath = (String) jsonPerson.get("profile_path");

                castMembers.add(new Person(actorName, charName, profilePath));
            }
            selectedMedia.setCast(castMembers);

            for (Person person : selectedMedia.getCast()){
                // if no picture don't show the actor
                if (person.getProfilePath() != null){
                    ImageView imageView = new ImageView("https://image.tmdb.org/t/p/w500" + person.getProfilePath());

                    Label label = new Label(MessageFormat.format("{0}\n as {1}", person.getName(), person.getCharacter()));

                    label.setAlignment(Pos.CENTER);

                    if (person.getName() == null) // Handle if actor plays themselves
                        label.setText(person.getName());

                    System.out.println(person.getName() + " plays " + person.getCharacter());

                    imageView.setFitWidth(142.4);
                    imageView.setFitHeight(210.4);
                    imageView.setPreserveRatio(false);

                    // Create VBox to hold the image and label
                    VBox actorBox = new VBox(0);
                    actorBox.getChildren().addAll(imageView, label);
                    actorBox.setAlignment(Pos.CENTER);

                    castContainer.getChildren().add(actorBox);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void populateSimilarToContainer(){
        similarToContainer.getChildren().clear();

        try {
            API api = new API();
            JSONArray results = api.getSimilarToMediaList(selectedMedia);
            if (results == null) return;

            for (int i = 0; i < Math.min(8, results.size()); i++) {
                JSONObject json = (JSONObject) results.get(i);
                Media media = MediaFactory.fromJson(json, "movie");
                if (media != null && media.getPosterPath() != null) {
                    ImageView imageView = new ImageView("https://image.tmdb.org/t/p/w500" + media.getPosterPath());
                    imageView.setFitWidth(178);
                    imageView.setFitHeight(263);
                    imageView.setPreserveRatio(false);

                    imageView.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                        AppState.getInstance().setSelectedMedia(media);
                        try {
                            SceneManager.switchTo("TvMovieDetailsPage.fxml");
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    });

                    similarToContainer.getChildren().add(imageView);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void populateRecommendationsContainer(){
        recommendationsContainer.getChildren().clear();

        try {
            API api = new API();
            JSONArray results = api.getRecommendationsList(selectedMedia);
            if (results == null) return;

            for (int i = 0; i < Math.min(8, results.size()); i++) {
                JSONObject json = (JSONObject) results.get(i);
                Media media = MediaFactory.fromJson(json, "movie");
                if (media != null && media.getPosterPath() != null) {
                    ImageView imageView = new ImageView("https://image.tmdb.org/t/p/w500" + media.getPosterPath());
                    imageView.setFitWidth(178);
                    imageView.setFitHeight(263);
                    imageView.setPreserveRatio(false);

                    imageView.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                        AppState.getInstance().setSelectedMedia(media);
                        try {
                            SceneManager.switchTo("TvMovieDetailsPage.fxml");
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    });

                    recommendationsContainer.getChildren().add(imageView);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onBackButtonClicked(ActionEvent actionEvent) throws IOException {
        SceneManager.switchTo("search.fxml");
    }

    @FXML
    public void onPlayTrailerClicked(ActionEvent actionEvent) throws IOException{

        try {
            API api = new API();
            String url = api.getMediaTrailerUrl(selectedMedia);

            if (url != null){
                Desktop.getDesktop().browse(new URI(url));
                System.out.println("Trailer opened in browser");
            }
            else{
                trailerErrorLabel.setText("Could not find Trailer");
                System.out.println("Could not find Trailer");
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
