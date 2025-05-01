package com.example.filmapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

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
        titleText.setText(selectedMedia.title);
        overviewText.setText(selectedMedia.overview);
        ratingText.setText(String.format("%s/10",selectedMedia.rating));
        posterImage.setImage(new Image("https://image.tmdb.org/t/p/w200" + selectedMedia.posterPath));
    }

    @FXML
    public void onBackButtonClicked(ActionEvent actionEvent) throws IOException {
        SceneManager.switchTo("search.fxml");
    }
}
