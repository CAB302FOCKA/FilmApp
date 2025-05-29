package com.example.filmapp;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserSessionTest {
    @Test
    public void testSetAndGetUserData() {
        UserSession.setUser("Test", "Test@example.com", "hashed");

        assertEquals("Test", UserSession.getUsername());
        assertEquals("Test@example.com", UserSession.getEmail());
        assertEquals("hashed", UserSession.getHashedPassword());
    }

    @Test
    public void testClearSession() {
        UserSession.setUser("Test", "Test@example.com", "secret");
        UserSession.clearSession();

        assertNull(UserSession.getUsername());
        assertNull(UserSession.getEmail());
        assertNull(UserSession.getHashedPassword());
    }
}