package com.example.filmapp.factory;

import com.example.filmapp.model.Media;
import com.example.filmapp.model.Movie;
import com.example.filmapp.model.TVSeries;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class MediaFactoryTest {

    @Test
    void testFromJson_withValidMovie() {
        JSONObject json = new JSONObject();
        json.put("id", 123);
        json.put("title", "Inception");
        json.put("overview", "A mind-bending thriller.");
        json.put("poster_path", "/poster.jpg");
        json.put("runtime", 148);
        json.put("vote_average", 8.8);

        JSONArray genres = new JSONArray();
        JSONObject genre = new JSONObject();
        genre.put("name", "Sci-Fi");
        genres.add(genre);
        json.put("genres", genres);

        Media result = MediaFactory.fromJson(json, "movie");

        assertNotNull(result);
        assertTrue(result instanceof Movie);
        assertEquals("Inception", result.getTitle());
        assertEquals(8.8, result.getRating(), 0.01);
        assertEquals(Arrays.asList("Sci-Fi"), result.getGenres());
    }

    @Test
    void testFromJson_withValidTVSeries() {
        JSONObject json = new JSONObject();
        json.put("id", 456);
        json.put("name", "Breaking Bad");
        json.put("overview", "A high school chemistry teacher turned meth kingpin.");
        json.put("poster_path", "/bb.jpg");
        json.put("runtime", 45);
        json.put("vote_average", 9.5);

        JSONArray genres = new JSONArray();
        JSONObject genre = new JSONObject();
        genre.put("name", "Crime");
        genres.add(genre);
        json.put("genres", genres);

        Media result = MediaFactory.fromJson(json, "tv");

        assertNotNull(result);
        assertTrue(result instanceof TVSeries);
        assertEquals("Breaking Bad", result.getTitle());
        assertEquals(9.5, result.getRating(), 0.01);
        assertEquals(Arrays.asList("Crime"), result.getGenres());
    }

    @Test
    void testFromJson_withMissingFields_defaultsApplied() {
        JSONObject json = new JSONObject();
        json.put("id", 789);
        json.put("vote_average", 7.0);
        // Missing title/name, overview, poster_path, genres, runtime

        Media result = MediaFactory.fromJson(json, "movie");

        assertNotNull(result);
        assertEquals("Unknown Title", result.getTitle());
        assertEquals("No overview available.", result.getOverview());
        assertEquals(0, result.getRuntime());
        assertEquals(7.0, result.getRating(), 0.01);
        assertTrue(result.getGenres().isEmpty());
    }

    @Test
    void testFromJson_withInvalidMediaType_returnsNull() {
        JSONObject json = new JSONObject();
        json.put("id", 101);
        json.put("title", "Unknown");

        Media result = MediaFactory.fromJson(json, "game");

        assertNull(result);
    }

    @Test
    void testFromJson_withMultiType_tvPreferredOverMovie() {
        JSONObject json = new JSONObject();
        json.put("id", 999);
        json.put("name", "The Office");
        json.put("overview", "Workplace comedy.");
        json.put("vote_average", 8.3);
        json.put("media_type", "tv");

        Media result = MediaFactory.fromJson(json, "multi");

        assertNotNull(result);
        assertTrue(result instanceof TVSeries);
        assertEquals("The Office", result.getTitle());
    }

    @Test
    void testFromJson_withInvalidJson_returnsNull() {
        JSONObject json = null;

        Media result = MediaFactory.fromJson(json, "movie");

        assertNull(result);
    }
}