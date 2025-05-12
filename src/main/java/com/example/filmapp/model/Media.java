package com.example.filmapp.model;

import java.util.ArrayList;

/**
 Standard structure for all items retrieved from the TMDB API
 */
public class Media {
    protected String id;
    protected String title;
    protected String overview;
    protected String posterPath;
    protected double rating;
    protected int runtime;
    ArrayList<String> genres;
    protected String mediaType;

    public Media(String id, String title, String overview, String posterPath, double rating, int runtime, ArrayList<String> genres) {
        this.id = id;
        this.title = title;
        this.overview = overview;
        this.posterPath = posterPath;
        this.rating = rating;
        this.runtime = runtime;
        this.genres = genres;
    }
    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
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

    public int getRuntime() {
        return runtime;
    }

    public ArrayList<String> getGenres() {
        return genres;
    }

    public String getMediaType(){
        return this.mediaType;
    }
}
