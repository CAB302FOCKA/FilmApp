package com.example.filmapp;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;

public class SearchController {
    @FXML
    private ImageView image1;
    @FXML
    private ImageView image2;
    @FXML
    private ImageView image3;
    @FXML
    private ImageView image4;
    @FXML
    private ImageView image5;
    @FXML
    private ImageView image6;
    @FXML
    private ImageView image7;
    @FXML
    private ImageView image8;
    @FXML
    private Label queryLabel;

    @FXML
    public void initialize() throws IOException {
        API api = new API();

        String query = "minecraft";

        JSONArray apiResults = api.searchMediaByTitle(query,"movie");
        ArrayList<String> searchResults = new ArrayList<String>();

        for (Object object : apiResults) {
            JSONObject obj = (JSONObject) object;
            String posterUrl = MediaFactory.fromJson(obj, "movie").posterPath;

            if (posterUrl != null) searchResults.add(posterUrl);
        }

        queryLabel.setText(MessageFormat.format("Showing results for \"{0}\"", query));

        image1.setImage(new Image("https://image.tmdb.org/t/p/w500" + searchResults.get(0)));
        image2.setImage(new Image("https://image.tmdb.org/t/p/w500" + searchResults.get(1)));
        image3.setImage(new Image("https://image.tmdb.org/t/p/w500" + searchResults.get(2)));
        image4.setImage(new Image("https://image.tmdb.org/t/p/w500" + searchResults.get(3)));
        image5.setImage(new Image("https://image.tmdb.org/t/p/w500" + searchResults.get(4)));
        image6.setImage(new Image("https://image.tmdb.org/t/p/w500" + searchResults.get(5)));
        image7.setImage(new Image("https://image.tmdb.org/t/p/w500" + searchResults.get(6)));
        image8.setImage(new Image("https://image.tmdb.org/t/p/w500" + searchResults.get(7)));
    }
}