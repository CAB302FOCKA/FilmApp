package com.example.filmapp.factory;

import com.example.filmapp.model.Media;
import com.example.filmapp.model.Movie;
import com.example.filmapp.model.TVSeries;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;

public class MediaFactory {
    /**
     Responsible for handling JSON objects from the TMDB API response and converting them into Media objects that can be easily used.
     */
    public static Media fromJson(JSONObject json, String mediaType) {
        try {
            String id = String.valueOf(json.get("id"));
            String title;

            if (mediaType.equalsIgnoreCase("movie")) {
                title = (String) json.getOrDefault("title", "Unknown Title");
            } else if (mediaType.equalsIgnoreCase("tv")) {
                title = (String) json.getOrDefault("name", "Unknown Title");
            } else if (mediaType.equalsIgnoreCase("multi")) {
                title = (String) json.getOrDefault("title", "Unknown Title");
                if (title.equals("Unknown Title")) {
                    title = (String) json.getOrDefault("name", "Unknown Title");
                }
            } else {
                return null;
            }

            String overview = (String) json.getOrDefault("overview", "No overview available.");
            String posterPath = (String) json.getOrDefault("poster_path", null);
            int runtime = (Integer) json.getOrDefault("runtime", 0);
            double rating = json.get("vote_average") instanceof Number ? ((Number) json.get("vote_average")).doubleValue() : 0.0;

            ArrayList<String> genres = new ArrayList<>();
            JSONArray genreObjectList = (JSONArray) json.getOrDefault("genres", null);

            if (genreObjectList != null) {
                for (Object object : genreObjectList) {
                    JSONObject obj = (JSONObject) object;
                    genres.add((String) obj.getOrDefault("name", null));
                }
            }

            if (mediaType.equalsIgnoreCase("movie")) {
                return new Movie(id, title, overview, posterPath, rating, runtime, genres);
            } else if (mediaType.equalsIgnoreCase("tv")) {
                return new TVSeries(id, title, overview, posterPath, rating, runtime, genres);
            } else {
                mediaType = (String) json.getOrDefault("media_type", null);
                if (mediaType.equalsIgnoreCase("movie")) {
                    return new Movie(id, title, overview, posterPath, rating, runtime, genres);
                } else if (mediaType.equalsIgnoreCase("tv")) {
                    return new TVSeries(id, title, overview, posterPath, rating, runtime, genres);
                } else {
                    return new Media(id, title, overview, posterPath, rating, runtime, genres);
                }
            }
        } catch (Exception e) {
            System.err.println("Error parsing media JSON: " + e.getMessage());
            return null;
        }
    }
}
