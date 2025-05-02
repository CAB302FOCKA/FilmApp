package com.example.filmapp.model;

import java.util.ArrayList;

/**
 Unused class intended to serve as a subclass to the Media class with TV-specific attributes
 */
public class TVSeries extends Media {
    protected String mediaType = "TV Series";

    public TVSeries(String id, String title, String overview, String posterPath, double rating, int runtime, ArrayList<String> genres) {
        super(id, title, overview, posterPath, rating, runtime, genres);
    }
}
