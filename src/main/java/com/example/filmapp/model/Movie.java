package com.example.filmapp.model;

import java.util.ArrayList;

/**
 Unused class intended to serve as a subclass to the Media class with Movie-specific attributes
*/
public class Movie extends Media {
    protected String mediaType = "Movie";

    public Movie(String id, String title, String overview, String posterPath, String backdropPath, double rating, int runtime, ArrayList<String> genres) {
        super(id, title, overview, posterPath, backdropPath, rating, runtime, genres);
    }
}
