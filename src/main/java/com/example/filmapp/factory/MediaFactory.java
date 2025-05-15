package com.example.filmapp.factory;

import com.example.filmapp.model.Media;
import com.example.filmapp.model.Movie;
import com.example.filmapp.model.TVSeries;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;

public class MediaFactory {
    /**
     * Responsible for handling JSON objects from the TMDB API response and converting them into Media objects that can be easily used.
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
            String backdropPath = (String) json.getOrDefault("backdrop_path", null);
            Object runtimeObj = json.get("runtime");
            int runtime = (runtimeObj instanceof Number) ? ((Number) runtimeObj).intValue() : 0;
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
                Movie movie = new Movie(id, title, overview, posterPath, backdropPath, rating, runtime, genres);
                movie.setMediaType("movie");
                return movie;
            } else if (mediaType.equalsIgnoreCase("tv")) {
                TVSeries tv = new TVSeries(id, title, overview, posterPath, backdropPath, rating, runtime, genres);
                tv.setMediaType("tv");
                return tv;
            } else {
                mediaType = (String) json.getOrDefault("media_type", null);
                if ("movie".equalsIgnoreCase(mediaType)) {
                    Movie movie = new Movie(id, title, overview, posterPath, backdropPath, rating, runtime, genres);
                    movie.setMediaType("movie");
                    return movie;
                } else if ("tv".equalsIgnoreCase(mediaType)) {
                    TVSeries tv = new TVSeries(id, title, overview, posterPath, backdropPath, rating, runtime, genres);
                    tv.setMediaType("tv");
                    return tv;
                } else {
                    Media media = new Media(id, title, overview, posterPath, backdropPath, rating, runtime, genres);
                    media.setMediaType(mediaType); // handles edge cases or unknowns
                    return media;
                }
            }
        } catch (Exception e) {
            System.err.println("Error parsing media JSON: " + e.getMessage());
            return null;
        }
    }
}
