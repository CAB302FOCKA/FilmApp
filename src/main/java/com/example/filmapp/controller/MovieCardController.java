package com.example.filmapp.controller;

import com.example.filmapp.model.Media;
import com.example.filmapp.state.AppState;
import com.example.filmapp.util.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class MovieCardController {
    @FXML private Label titleLabel;
    @FXML private Label descriptionLabel;
    @FXML private ImageView imageView;

    public void setData(String title, String description, String imageUrl, Media media) {
        titleLabel.setText(title);
        descriptionLabel.setText(description);
        imageView.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            AppState.getInstance().setSelectedMedia(media);
            try {
                SceneManager.switchTo("TvMovieDetailsPage.fxml");
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }});

            if (imageUrl != null && !imageUrl.isEmpty()) imageView.setImage(new Image(imageUrl));
        }
    }
