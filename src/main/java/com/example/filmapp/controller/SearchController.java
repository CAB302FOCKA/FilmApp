package com.example.filmapp.controller;

import com.example.filmapp.model.Media;
import com.example.filmapp.factory.MediaFactory;
import com.example.filmapp.service.API;
import com.example.filmapp.state.AppState;
import com.example.filmapp.util.SceneManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.text.MessageFormat;

public class SearchController {

    @FXML
    private ComboBox filterComboBox;
    @FXML
    private Label queryLabel;
    @FXML
    private FlowPane flowPane;
    @FXML
    private TextField queryTextField;

    @FXML
    public void initialize() {
        filterComboBox.getItems().addAll("Movies & TV", "Movies", "TV Shows");
        filterComboBox.setValue("Movies & TV");
    }

    @FXML
    protected void handleSearchButtonAction(ActionEvent event) throws IOException {
        flowPane.getChildren().clear();

        String mediaType = filterComboBox.getValue().toString();
        switch (mediaType) {
            case "Movies & TV": mediaType = "multi"; break;
            case "Movies": mediaType = "movie"; break;
            case "TV Shows": mediaType = "tv"; break;
        }

        API api = new API();
        JSONArray apiResults = api.searchMediaByTitle(queryTextField.getText(), mediaType);

        if (apiResults == null) {
            queryLabel.setText("No results found or API error.");
            return;
        }

        for (Object object : apiResults) {
            JSONObject obj = (JSONObject) object;
            Media media = MediaFactory.fromJson(obj, mediaType);

            if (media == null || media.getPosterPath() == null) continue;

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

            flowPane.getChildren().add(imageView);
        }

        queryLabel.setText(MessageFormat.format("Showing results for \"{0}\"", queryTextField.getText()));
    }

    @FXML
    private void handleBackController() throws IOException {
        SceneManager.switchTo("home_discover2.fxml");
    }
}
