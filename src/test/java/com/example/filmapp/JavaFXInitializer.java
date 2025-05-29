package com.example.filmapp;

import javafx.application.Platform;

import java.util.concurrent.CountDownLatch;

public class JavaFXInitializer {

    private static boolean started = false;

    public static void initToolkit() {
        if (!started) {
            CountDownLatch latch = new CountDownLatch(1);
            Platform.startup(() -> {
                started = true;
                latch.countDown();
            });
            try {
                latch.await();
            } catch (InterruptedException e) {
                throw new RuntimeException("Failed to initialize JavaFX", e);
            }
        }
    }
}