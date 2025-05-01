package com.example.filmapp.controller;

import com.example.filmapp.model.Media;
import com.example.filmapp.state.AppState;
import com.example.filmapp.util.SceneManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

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
    public void initialize(){
        titleText.setText(selectedMedia.getTitle());
        overviewText.setText(selectedMedia.getOverview());
        ratingText.setText(String.format("%s/10",selectedMedia.getRating()));
        posterImage.setImage(new Image("https://image.tmdb.org/t/p/w200" + selectedMedia.getPosterPath()));
    }

    @FXML
    public void onBackButtonClicked(ActionEvent actionEvent) throws IOException {
        SceneManager.switchTo("search.fxml");
    }
}
