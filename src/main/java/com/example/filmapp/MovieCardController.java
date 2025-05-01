package com.example.filmapp;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class MovieCardController {
    @FXML private Label titleLabel;
    @FXML private Label descriptionLabel;
    @FXML private ImageView imageView;

    public void setData(String title, String description, String imageUrl) {
        titleLabel.setText(title);
        descriptionLabel.setText(description);

        if (imageUrl != null && !imageUrl.isEmpty()) {
            imageView.setImage(new Image(imageUrl));
        }
    }
}

