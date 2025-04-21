package com.example.filmapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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
    private Label queryLabel;
    @FXML
    private FlowPane flowPane;
    @FXML
    private TextField queryTextField;
    @FXML
    private Button searchButton;

    @FXML
    public void initialize() throws IOException {}

    @FXML
    protected void handleSearchButtonAction(ActionEvent event) throws IOException {
        flowPane.getChildren().clear();

        API api = new API();

        JSONArray apiResults = api.searchMediaByTitle(queryTextField.getText(),"tv");
        ArrayList<String> searchResults = new ArrayList<String>();

        for (Object object : apiResults) {
            JSONObject obj = (JSONObject) object;
            String posterUrl = MediaFactory.fromJson(obj, "tv").posterPath;

            System.out.println(posterUrl);

            if (posterUrl != null) {
                ImageView imageView = new ImageView("https://image.tmdb.org/t/p/w500" + posterUrl);
                imageView.setFitWidth(178);
                imageView.setFitHeight(263);
                imageView.setPreserveRatio(true);

                flowPane.getChildren().add(imageView);
            }
        }

        queryLabel.setText(MessageFormat.format("Showing results for \"{0}\"", queryTextField.getText()));
    }
}