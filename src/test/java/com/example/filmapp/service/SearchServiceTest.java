package com.example.filmapp.service;


import com.example.filmapp.model.Media;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SearchServiceTest {

    static class MockAPI extends API {
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

    @Test
    void testSearch_returnsCorrectMediaList() throws IOException {
        SearchService service = new SearchService(new MockAPI());

        List<Media> result = service.search("test", "Movies");

        assertEquals(1, result.size());
        assertEquals("Test Movie", result.get(0).getTitle());
    }

    @Test
    void testSearchNoResults_returnsEmptyList() throws IOException {
        SearchService service = new SearchService(new API() {
            @Override
            public JSONArray searchMediaByTitle(String query, String type) {
                return new JSONArray(); // simulate no results
            }
        });
        List<Media> result = service.search("noresults", "Movies");
        assertTrue(result.isEmpty());
    }
}