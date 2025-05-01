package com.example.filmapp;

public class AppState {
    private static Media selectedMedia;

    public static void setSelectedMedia(Media media) {
        selectedMedia = media;
    }

    public static Media getSelectedMedia() {
        return selectedMedia;
    }
}
