package com.example.filmapp.state;

import com.example.filmapp.model.Media;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AppStateTest {
    @Test
    public void testSingletonInstance() {
        AppState state1 = AppState.getInstance();
        AppState state2 = AppState.getInstance();
        assertSame(state1, state2);
    }

    @Test
    public void testSettersAndGetters() {
        AppState appState = AppState.getInstance();

        Media dummyMedia = new Media("id", "title", "overview", "poster", "backdrop", 0.0, 0, null) {};
        appState.setSelectedMedia(dummyMedia);
        appState.setCurrentUserId("user123");

        assertEquals(dummyMedia, appState.getSelectedMedia());
        assertEquals("user123", appState.getCurrentUserId());
    }

    @Test
    void testUserInfoRetrievalFromDB_simulatedViaAppState() {
        AppState appState = AppState.getInstance();
        appState.setCurrentUserId("testUserId");
        assertEquals("testUserId", appState.getCurrentUserId());
    }
}