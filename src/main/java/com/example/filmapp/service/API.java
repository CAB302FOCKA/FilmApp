package com.example.filmapp.service;

import com.example.filmapp.factory.MediaFactory;
import com.example.filmapp.model.Media;
import com.example.filmapp.state.AppState;
import com.example.filmapp.util.SceneManager;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.text.MessageFormat;

/**
 * Responsible for sending requests to the TMDB API and handling the response.
 */
public class API {

    public JSONArray searchMediaByTitle(String titleQuery, String mediaType) throws IOException {
        JSONArray combinedResults = new JSONArray();

        for (int page = 1; page <= 3; page++) {  // Adjust number of pages as needed
            String url = MessageFormat.format(
                    "https://api.themoviedb.org/3/search/{0}?query={1}&include_adult=false&language=en-US&page={2}",
                    mediaType, titleQuery, page
            );

            HttpRequest httpRequest = new HttpRequest(url);
            JSONObject jsonResponse = httpRequest.Fetch();

            if (jsonResponse == null || !jsonResponse.containsKey("results")) {
                System.err.println("API Error on page " + page);
                break;
            }

            JSONArray pageResults = (JSONArray) jsonResponse.get("results");
            combinedResults.addAll(pageResults);

            // Optional: break early if fewer than 20 results returned
            if (pageResults.size() < 20) break;
        }

        return combinedResults;
    }

    public JSONObject getMediaDetails(String mediaType, String id) throws IOException {
        String url = MessageFormat.format("https://api.themoviedb.org/3/{0}/{1}", mediaType, id);
        HttpRequest httpRequest = new HttpRequest(url);
        return httpRequest.Fetch();
    }

    public JSONArray getTrendingMediaList(String mediaType) throws IOException {
        String url = MessageFormat.format("https://api.themoviedb.org/3/trending/{0}/day", mediaType);
        HttpRequest httpRequest = new HttpRequest(url);
        JSONObject jsonResponse = httpRequest.Fetch();

        if (jsonResponse == null || !jsonResponse.containsKey("results")) {
            System.err.println("API Error: No trending results found.");
            return null;
        }

        return (JSONArray) jsonResponse.get("results");
    }

    public JSONArray getSimilarToMediaList(Media media) throws IOException {
        String url = MessageFormat.format("https://api.themoviedb.org/3/{0}/{1}/similar", media.getMediaType(),media.getId());
        HttpRequest httpRequest = new HttpRequest(url);
        JSONObject jsonResponse = httpRequest.Fetch();

        if (jsonResponse == null || !jsonResponse.containsKey("results")) {
            System.err.println("API Error: No similar movies found.");
            return null;
        }

        return (JSONArray) jsonResponse.get("results");
    }

    public JSONArray getRecommendationsList(Media media) throws IOException {
        String url = MessageFormat.format("https://api.themoviedb.org/3/{0}/{1}/recommendations", media.getMediaType(),media.getId());
        HttpRequest httpRequest = new HttpRequest(url);
        JSONObject jsonResponse = httpRequest.Fetch();

        if (jsonResponse == null || !jsonResponse.containsKey("results")) {
            System.err.println("API Error: No recommendations found.");
            return null;
        }

        return (JSONArray) jsonResponse.get("results");
    }

    public String getMediaTrailerUrl(Media media) throws IOException{
        String url = MessageFormat.format(" https://api.themoviedb.org/3/{0}/{1}/videos", media.getMediaType(),media.getId());
        HttpRequest httpRequest = new HttpRequest(url);
        JSONObject jsonResponse = httpRequest.Fetch();

        if (jsonResponse == null || !jsonResponse.containsKey("results")) {
            System.err.println("API Error: No trailer found.");
            return null;
        }

        String trailerUrl = null;

        JSONArray results = (JSONArray) jsonResponse.get("results");

        // Loop through all the possible videos and play the first to meet the criteria trailer on YouTube.
        for (int i = 0; i < results.size(); i++) {
            JSONObject video = (JSONObject) results.get(i);
            String type = (String) video.get("type");
            String site = (String) video.get("site");

            if ("Trailer".equalsIgnoreCase(type) && "YouTube".equalsIgnoreCase(site)) {
                String key = (String) video.get("key");
                trailerUrl = "https://www.youtube.com/watch?v=" + key;
                System.out.println("Trailer URL: " + trailerUrl);
                break;
            }
        }

        return trailerUrl;
    }
}
