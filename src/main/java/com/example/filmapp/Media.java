package com.example.filmapp;

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
}
