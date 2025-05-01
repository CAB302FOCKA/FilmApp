package com.example.filmapp.model;

/**
 Standard structure for all items retrieved from the TMDB API
 */
public class Media {
    protected String id;
    protected String title;
    protected String overview;
    protected String posterPath;
    protected double rating;

    public Media(String id, String title, String overview, String posterPath, double rating) {
        this.id = id;
        this.title = title;
        this.overview = overview;
        this.posterPath = posterPath;
        this.rating = rating;
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public double getRating() {
        return rating;
    }
}
