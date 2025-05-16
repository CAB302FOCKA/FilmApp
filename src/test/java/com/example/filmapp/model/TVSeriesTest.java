package com.example.filmapp.model;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

class TVSeriesTest {
    @Test
    void testTVSeriesConstructorAndFields() {
        ArrayList<String> genres = new ArrayList<>();
        genres.add("Drama");

        TVSeries tv = new TVSeries(
                "456",
                "Test Series",
                "A TV show test.",
                "/tvposter.jpg",
                "/tvbackdrop.jpg",
                7.9,
                45,
                genres
        );

        assertEquals("456", tv.id);
        assertEquals("Test Series", tv.title);
        assertEquals("A TV show test.", tv.overview);
        assertEquals("/tvposter.jpg", tv.posterPath);
        assertEquals("/tvbackdrop.jpg", tv.backdropPath);
        assertEquals(7.9, tv.rating);
        assertEquals(45, tv.runtime);
        assertEquals("TV Series", tv.mediaType);
        assertEquals(genres, tv.genres);
    }
}