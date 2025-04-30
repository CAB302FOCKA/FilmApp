package com.example.filmapp;

import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Button;

public class HomeDiscoverController {

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private GridPane movieContainer;

    @FXML
    private Button search;

    @FXML
    public void initialize() {
        System.out.println("HomeDiscoverController initialized");
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
}

