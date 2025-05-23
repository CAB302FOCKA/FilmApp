package com.example.filmapp.service;

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

    public JSONArray getSimilarToMovieList(String mediaId) throws IOException {
        String url = MessageFormat.format("https://api.themoviedb.org/3/movie/{0}/similar", mediaId);
        HttpRequest httpRequest = new HttpRequest(url);
        JSONObject jsonResponse = httpRequest.Fetch();

        if (jsonResponse == null || !jsonResponse.containsKey("results")) {
            System.err.println("API Error: No similar movies found.");
            return null;
        }

        return (JSONArray) jsonResponse.get("results");
    }
    public JSONArray getRecommendationsList(String mediaId) throws IOException {
        String url = MessageFormat.format("https://api.themoviedb.org/3/movie/{0}/recommendations", mediaId);
        HttpRequest httpRequest = new HttpRequest(url);
        JSONObject jsonResponse = httpRequest.Fetch();

        if (jsonResponse == null || !jsonResponse.containsKey("results")) {
            System.err.println("API Error: No recommendations found.");
            return null;
        }

        return (JSONArray) jsonResponse.get("results");
    }
}
