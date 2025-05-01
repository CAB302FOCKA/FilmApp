package com.example.filmapp.state;

import com.example.filmapp.model.Media;

public class AppState {
    private static Media selectedMedia;

    public static void setSelectedMedia(Media media) {
        selectedMedia = media;
    }

    public static Media getSelectedMedia() {
        return selectedMedia;
    }
}
