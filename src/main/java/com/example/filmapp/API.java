package com.example.filmapp;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.text.MessageFormat;

public class API {
    public JSONObject getSeriesOverview(String titleQuery) throws IOException {
        String url = MessageFormat.format("https://api.themoviedb.org/3/search/tv?query={0}&include_adult=false&language=en-US&page=1", titleQuery);
        HttpRequest httpRequest = new HttpRequest(url);
        JSONObject jsonResponse = httpRequest.Fetch();

        JSONArray results  = (JSONArray) jsonResponse.get("results");
        return (JSONObject) results.getFirst();
    }

    public String getSeriesImage(Long id) throws IOException {
        String url = MessageFormat.format("https://api.themoviedb.org/3/tv/{0,number,#}/images?language=en", id);
        HttpRequest httpRequest = new HttpRequest(url);
        JSONObject jsonResponse = httpRequest.Fetch();

        JSONArray results  = (JSONArray) jsonResponse.get("posters");
        JSONObject firstResult = (JSONObject) results.getFirst();

        String posterImageURL = (String) firstResult.get("file_path");

        return (MessageFormat.format("https://image.tmdb.org/t/p/w500{0}", posterImageURL));
    }
}