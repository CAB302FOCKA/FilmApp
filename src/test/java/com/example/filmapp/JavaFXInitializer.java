package com.example.filmapp;

import com.example.filmapp.util.SceneManager;
import javafx.application.Platform;

import java.util.concurrent.CountDownLatch;

public class JavaFXInitializer {

    private static boolean initialized = false;

    public static synchronized void initJavaFX() {
        if (initialized || Platform.isFxApplicationThread()) return;

        try {
            CountDownLatch latch = new CountDownLatch(1);
            Platform.startup(() -> {
                initialized = true;
                latch.countDown();
            });
            latch.await();
        } catch (IllegalStateException alreadyStarted) {
            // JavaFX already initialized
            initialized = true;
        } catch (InterruptedException e){
            throw new RuntimeException("Failed to initialize JavaFX", e);
        }
    }
}