package com.example.filmapp.controller;

import com.example.filmapp.factory.MediaFactory;
import com.example.filmapp.model.Media;
import com.example.filmapp.service.API;
import com.example.filmapp.state.AppState;
import com.example.filmapp.util.SceneManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;

public class MediaDetailsController {
    Media selectedMedia = AppState.getSelectedMedia();

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
    public void initialize(){
        titleText.setText(selectedMedia.getTitle());
        overviewText.setText(selectedMedia.getOverview());
        ratingText.setText(String.format("%s/10",selectedMedia.getRating()));
        posterImage.setImage(new Image("https://image.tmdb.org/t/p/w200" + selectedMedia.getPosterPath(), true));

        populateSimilarToContainer();
        populateRecommendationsContainer();
    }

    void populateSimilarToContainer(){
        similarToContainer.getChildren().clear();

        try {
            API api = new API();
            JSONArray results = api.getSimilarToMovieList(selectedMedia.getId());
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
                        AppState.setSelectedMedia(media);
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
            JSONArray results = api.getRecommendationsList(selectedMedia.getId());
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
                        AppState.setSelectedMedia(media);
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
}
