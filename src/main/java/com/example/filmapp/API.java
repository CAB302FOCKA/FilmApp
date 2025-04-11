package com.example.filmapp;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.text.MessageFormat;

public class API {
    public JSONArray searchMediaByTitle(String titleQuery, String mediaType) throws IOException {
        String url = MessageFormat.format("https://api.themoviedb.org/3/search/{0}?query={1}&include_adult=false&language=en-US&page=1", mediaType, titleQuery);
        HttpRequest httpRequest = new HttpRequest(url);
        JSONObject jsonResponse = httpRequest.Fetch();

        return (JSONArray) jsonResponse.get("results");
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

        return (JSONArray) jsonResponse.get("results");
    }
}