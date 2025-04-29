package com.example.filmapp;

import org.json.simple.JSONObject;

public class MediaFactory {
    /**
     Responsible for handling JSON objects from the TMDB API response and converting them into Media objects that can be easily used.
     */
    public static Media fromJson(JSONObject json, String mediaType) {
        String id = String.valueOf(json.get("id"));
        String title;
        String overview;
        String posterPath;
        double rating;

        // Use different keys for TV shows
        if (mediaType.equalsIgnoreCase("movie")) { title = (String) json.getOrDefault("title", "Unknown Title");}
        else if (mediaType.equalsIgnoreCase("tv")) { title = (String) json.getOrDefault("name", "Unknown Title");}
        else if (mediaType.equalsIgnoreCase("multi")) {
            title = (String) json.getOrDefault("title", "Unknown Title");
            if (title.equals("Unknown Title")) {
                title = (String) json.getOrDefault("name", "Unknown Title");
            }
        }
        else { throw new IllegalArgumentException("Unsupported media type: " + mediaType);}

        overview = (String) json.getOrDefault("overview", "No overview available.");
        posterPath = (String) json.getOrDefault("poster_path", null);
        rating = json.get("vote_average") != null
                ? ((Number) json.get("vote_average")).doubleValue()
                : 0.0;

        // Return the appropriate subclass
        switch (mediaType.toLowerCase()) {
            case "movie":
                return new Media(id, title, overview, posterPath, rating);
            case "tv":
                return new Media(id, title, overview, posterPath, rating);
            case "multi":
                return new Media(id, title, overview, posterPath, rating);
            default:
                throw new IllegalArgumentException("Unknown media type: " + mediaType);
        }
    }
}
