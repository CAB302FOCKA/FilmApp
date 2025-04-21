package com.example.filmapp;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class SearchController {
    @FXML
    private ImageView image1;

    @FXML
    public void initialize() {
        API api = new API();
    }
}