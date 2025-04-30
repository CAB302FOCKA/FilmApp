package com.example.filmapp;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class movie_card_controller {

    @FXML private ImageView imageView;
    @FXML private Label titleLabel;
    @FXML private Label descLabel;

    public void setData(String title, String description, String imageUrl) {
        titleLabel.setText(title);
        descLabel.setText(description);
        if (imageUrl != null && !imageUrl.isEmpty()) {
            imageView.setImage(new Image(imageUrl));
        }
    }
}
