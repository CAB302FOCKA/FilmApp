package com.example.filmapp;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.io.IOException;
import java.text.MessageFormat;

/**
 *  This class interacts with the TMDB (The Movie Database) API and is used to fetch data from it
 *  Do not extend from this class
 *  Please note This is a temporary class and will be refactored to being a child of an abstract class that handles HTTP requests and this class will be used solely for fetching data from TMDB
 *  - Kyha
 */
public class TMDB {
    String title;
    String overview;
    String poster_path;
    Long id;

    public void getSeriesOverview(String titleQuery) throws IOException {
        String url = MessageFormat.format("https://api.themoviedb.org/3/search/tv?query={0}&include_adult=false&language=en-US&page=1", titleQuery);
        HttpRequest httpRequest = new HttpRequest(url);
        JSONObject jsonResponse = httpRequest.Fetch();

        JSONArray results = (JSONArray) jsonResponse.get("results");
        if (results.isEmpty()) {
            throw new IOException("No results found for query: " + titleQuery);
        }

        JSONObject firstResult = (JSONObject) results.getFirst();

        this.title = (String) firstResult.get("original_name");
        this.overview = (String) firstResult.get("overview");
        this.poster_path = (String) firstResult.getOrDefault("poster_path", null);
        this.id = (Long) firstResult.get("id");
    }


    public String getImageUrl() {
        if (poster_path != null) {
            return "https://image.tmdb.org/t/p/w500" + poster_path;
        }
        return null;
    }

}
