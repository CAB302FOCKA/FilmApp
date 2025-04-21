package com.example.filmapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;

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
    private ComboBox sortComboBox;

    @FXML
    public void initialize() {
        filterComboBox.getItems().addAll("Movies & TV","Movies","TV Shows");
        filterComboBox.setValue("Movies & TV");

        sortComboBox.getItems().addAll("Relevance","Popularity","Rating");
        sortComboBox.setValue("Relevance");
    }

    @FXML
    protected void handleSearchButtonAction(ActionEvent event) throws IOException {
        //Clear previous search results
        flowPane.getChildren().clear();

        String mediaType = filterComboBox.getValue().toString();

        switch (mediaType){
            case "Movies & TV":
                mediaType = "multi";
                break;
            case "Movies":
                mediaType = "movie";
                break;
            case "TV Shows":
                mediaType = "tv";
                break;
        }

        API api = new API();
        JSONArray apiResults = api.searchMediaByTitle(queryTextField.getText(), mediaType);

        //Loop through list of media
        for (Object object : apiResults) {
            JSONObject obj = (JSONObject) object;
            String posterUrl = MediaFactory.fromJson(obj, mediaType).posterPath;

            System.out.println(posterUrl);

            // Skip to next item
            if (posterUrl == null) continue;

            ImageView imageView = new ImageView("https://image.tmdb.org/t/p/w500" + posterUrl);

            // 2:3 aspect ratio for poster
            int posterLength = 178;
            int posterHeight = 263;

            imageView.setFitWidth(posterLength);
            imageView.setFitHeight(posterHeight);

            imageView.setPreserveRatio(true);

            flowPane.getChildren().add(imageView);
        }
        queryLabel.setText(MessageFormat.format("Showing results for \"{0}\"", queryTextField.getText()));
    }
}