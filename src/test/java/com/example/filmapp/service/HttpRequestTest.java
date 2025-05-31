package com.example.filmapp.service;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class HttpRequestTest {
    @Test
    public void testRequest_InvalidUrl_ThrowsIOException() {
        HttpRequest request = new HttpRequest("http://invalid.url");

        assertThrows(IOException.class, request::Fetch);
    }

    @Test
    void testRequest_ValidUrl() {
        HttpRequest request = new HttpRequest("https://httpbin.org/get?query=test");

        assertDoesNotThrow(request::Fetch);
    }

    @Test
    void testRequest_404Response() {
        HttpRequest request = new HttpRequest("https://httpbin.org/status/404");

        assertDoesNotThrow(request::Fetch);
    }
}