package com.example.filmapp.service;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class HttpRequestTest {
    @Test
    public void testInvalidUrlThrowsIOException() {
        HttpRequest request = new HttpRequest("http://invalid.url");

        assertThrows(IOException.class, request::Fetch);
    }
}