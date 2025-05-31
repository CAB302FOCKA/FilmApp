package com.example.filmapp.service;


import com.example.filmapp.model.Media;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SearchServiceTest {

    static class MockAPIWithResult extends API {
        @Override
        public JSONArray searchMediaByTitle(String query, String type) {
            JSONArray array = new JSONArray();

            JSONObject movie = new JSONObject();
            movie.put("id", 1);
            movie.put("title", "Test Movie");
            movie.put("media_type", "movie");

            array.add(movie);
            return array;
        }
    }

    static class MockAPIWithNoResult extends API {
        @Override
        public JSONArray searchMediaByTitle(String query, String type) {
            return new JSONArray(); // Simulates no results found
        }
    }

    // -------- Test Cases --------

    @Test
    void search_returnsResults_whenMediaFound() throws IOException {
        SearchService service = new SearchService(new MockAPIWithResult());

        List<Media> result = service.search("test", "Movies");

        assertEquals(1, result.size(), "Expected one result");
        assertEquals("Test Movie", result.get(0).getTitle());
        assertEquals("movie", result.get(0).getMediaType());
    }

    @Test
    void search_returnsEmptyList_whenNoResultsFound() throws IOException {
        SearchService service = new SearchService(new MockAPIWithNoResult());

        List<Media> result = service.search("noresults", "Movies");

        assertNotNull(result, "Result list should not be null");
        assertTrue(result.isEmpty(), "Expected empty result list");
    }
}