package com.example.filmapp.service;

import com.example.filmapp.factory.MediaFactory;
import com.example.filmapp.model.Media;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Service that manages search page
 */
public class SearchService {
    private final API api;

    public SearchService(API api) {
        this.api = api;
    }

    public List<Media> search(String query, String filter) throws IOException {
        String mediaType = switch (filter) {
            case "Movies" -> "movie";
            case "TV Shows" -> "tv";
            default -> "multi";
        };

        JSONArray jsonArray = api.searchMediaByTitle(query, mediaType);
        List<Media> results = new ArrayList<>();

        for (Object obj : jsonArray) {
            JSONObject json = (JSONObject) obj;
            Media media = MediaFactory.fromJson(json, mediaType);
            if (media != null) results.add(media);
        }

        return results;
    }
}
