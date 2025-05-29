package com.example.filmapp.controller;

import com.example.filmapp.JavaFXInitializer;
import javafx.application.Platform;
import javafx.scene.control.ComboBox;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;

import static org.junit.jupiter.api.Assertions.*;

class SearchControllerTest {

    @BeforeAll
    public static void initToolkit() throws Exception {
        JavaFXInitializer.initToolkit();
    }

    @Test
    public void testComboBoxOptionsInitialization() {
        SearchController controller = new SearchController();
        controller.filterComboBox = new ComboBox<>();
        controller.initialize();

        assertTrue(controller.filterComboBox.getItems().contains("Movies & TV"));
        assertEquals("Movies & TV", controller.filterComboBox.getValue());
    }
}