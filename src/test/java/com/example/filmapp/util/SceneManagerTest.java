package com.example.filmapp.util;

import javafx.application.Platform;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class SceneManagerTest {

    @BeforeAll
    public static void initJavaFX() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.startup(latch::countDown);
        latch.await();
    }

    @Test
    public void testInitializeDoesNotThrow() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        assertDoesNotThrow(() -> {
            Platform.runLater(() -> {
                try {
                    Stage mockStage = new Stage();
                    SceneManager.initialize(mockStage);
                } finally {
                    latch.countDown();
                }
            });
            latch.await(5, TimeUnit.SECONDS);
        });
    }
}