package com.example.filmapp.controller;

import com.example.filmapp.JavaFXInitializer;
import javafx.application.Platform;
import java.util.concurrent.CountDownLatch;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LoginControllerTest {
    private LoginController controller;

    @BeforeAll
    public static void initToolkit() throws Exception {
        JavaFXInitializer.initJavaFX();
    }

    @BeforeEach
    public void setUp() {
        controller = new LoginController();
        controller.emailField = new TextField();
        controller.passwordField = new TextField();
        controller.loginStatus = new Label();
    }

    @Test
    public void testEmptyFieldsValidation() {
        controller.emailField.setText("");
        controller.passwordField.setText("");
        controller.handleLogin(new ActionEvent());
        assertEquals("Please fill in all fields.", controller.loginStatus.getText());
    }
}