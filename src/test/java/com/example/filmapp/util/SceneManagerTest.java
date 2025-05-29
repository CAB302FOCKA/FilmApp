package com.example.filmapp.util;

import com.example.filmapp.JavaFXInitializer;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SceneManagerTest {

    @BeforeAll
    public static void initializeTest() throws Exception {
        SceneManager.enableTestMode();
    }

    @Test
    public void testInvalidFxmlThrows() {
        assertThrows(RuntimeException.class, () -> {
            SceneManager.switchTo("someScreen.fxml");  // Will only log in test mode
        });
    }

    @Test
    public void testSwitchToValidPage() {
        assertDoesNotThrow(() -> {
            SceneManager.switchTo("privacy_data.fxml");  // Will only log in test mode
        });
    }
}