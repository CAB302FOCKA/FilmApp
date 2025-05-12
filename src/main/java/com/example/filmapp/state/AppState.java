package com.example.filmapp.state;

import com.example.filmapp.model.Media;

public class AppState {
    private static AppState instance;
    private Media selectedMedia;
    private String currentUserId;

    private AppState() {
        // Private constructor to enforce singleton
    }

    public static AppState getInstance() {
        if (instance == null) {
            instance = new AppState();
        }
        return instance;
    }

    public Media getSelectedMedia() {
        return selectedMedia;
    }

    public void setSelectedMedia(Media media) {
        this.selectedMedia = media;
    }

    public String getCurrentUserId() {
        return currentUserId;
    }

    public void setCurrentUserId(String currentUserId) {
        this.currentUserId = currentUserId;
    }
}
