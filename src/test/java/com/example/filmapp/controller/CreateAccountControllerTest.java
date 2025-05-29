package com.example.filmapp.controller;

import com.example.filmapp.JavaFXInitializer;
import javafx.application.Platform;
import javafx.scene.control.TextField;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;

import static org.junit.jupiter.api.Assertions.*;

class CreateAccountControllerTest {
    private CreateAccountController controller;

    @BeforeAll
    public static void initToolkit() throws Exception {
        JavaFXInitializer.initJavaFX();
    }

    @BeforeEach
    public void setUp() {
        controller = new CreateAccountController();
        controller.myUsername = new TextField();
        controller.myEmail = new TextField();
        controller.myPass = new TextField();
        controller.myConfPass = new TextField();

        controller.username = "";
        controller.email = "";
        controller.pass = "";
        controller.confPass = "";
    }

    @Test
    public void testAllFieldsEmpty() {
        controller.username = "";
        controller.email = "";
        controller.pass = "";
        controller.confPass = "";

        assertFalse(controller.validateInputs());
    }

    @Test
    public void testMismatchedPasswords() {
        controller.username = "user";
        controller.email = "email@example.com";
        controller.pass = "pass1";
        controller.confPass = "pass2";

        assertFalse(controller.validateInputs());
    }

    @Test
    public void testInvalidEmailFormat() {
        controller.username = "user";
        controller.email = "not-an-email";
        controller.pass = "password";
        controller.confPass = "password";

        assertFalse(controller.validateInputs());
    }

    @Test
    public void testValidInputs() {
        controller.username = "user";
        controller.email = "email@example.com";
        controller.pass = "password";
        controller.confPass = "password";

        assertTrue(controller.validateInputs());
    }
}