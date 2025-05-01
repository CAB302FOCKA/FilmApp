package com.example.filmapp;

import org.json.simple.JSONObject;

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
            double rating = json.get("vote_average") instanceof Number ? ((Number) json.get("vote_average")).doubleValue() : 0.0;

            return new Media(id, title, overview, posterPath, rating);
        } catch (Exception e) {
            System.err.println("Error parsing media JSON: " + e.getMessage());
            return null;
        }
    }
}
