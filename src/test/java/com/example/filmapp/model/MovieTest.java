package com.example.filmapp.model;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

class MovieTest {

    @Test
    void testMovieConstructorAndFields() {
        ArrayList<String> genres = new ArrayList<>();
        genres.add("Action");
        genres.add("Adventure");

        Movie movie = new Movie(
                "123",
                "Test Movie",
                "This is a test.",
                "/poster.jpg",
                "/backdrop.jpg",
                8.5,
                120,
                genres
        );

        assertEquals("123", movie.id);
        assertEquals("Test Movie", movie.title);
        assertEquals("This is a test.", movie.overview);
        assertEquals("/poster.jpg", movie.posterPath);
        assertEquals("/backdrop.jpg", movie.backdropPath);
        assertEquals(8.5, movie.rating);
        assertEquals(120, movie.runtime);
        assertEquals(genres, movie.genres);
        assertEquals("Movie", movie.mediaType);
    }

    @Test
    void testSetCastAndMediaType() {
        Movie movie = new Movie("1", "Title", "", "", "", 0.0, 0, new ArrayList<>());
        ArrayList<Person> cast = new ArrayList<>();
        cast.add(new Person("Actor", "Character", "/path.jpg"));

        movie.setCast(cast);
        movie.setMediaType("Movie");

        assertEquals("Movie", movie.mediaType);
        assertEquals(1, movie.cast.size());
    }
}