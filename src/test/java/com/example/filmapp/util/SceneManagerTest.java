package com.example.filmapp.util;

import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SceneManagerTest {
    @Test
    public void testInitializeDoesNotThrow() {
        assertDoesNotThrow(() -> {
            Stage mockStage = new Stage();
            SceneManager.initialize(mockStage);
        });
    }
}