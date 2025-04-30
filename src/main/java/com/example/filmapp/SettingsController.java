package com.example.filmapp;

import javafx.fxml.FXML;

import java.io.IOException;

public class SettingsController {

    @FXML
    protected void handleBack() {
        System.out.println("Back button clicked.");
        // Navigate to previous screen
    }
    @FXML
    protected void handleAccount() {
        System.out.println("Account settings clicked.");
        // Navigate to account settings
    }

    @FXML
    protected void handleHidden() {
        System.out.println("Hidden settings clicked.");
        // Show hidden settings
    }

    @FXML
    protected void handlePrivacy() {
        System.out.println("Privacy & Data clicked.");
        // Show privacy settings
    }

    @FXML
    protected void handleTerms() {
        System.out.println("Terms and Conditions clicked.");
        // Show T&Cs
    }

    @FXML
    protected void handleLogout() {
        System.out.println("Logging out...");
        // Perform logout
    }
    @FXML
    private void handleBackController() throws IOException {
        SceneManager.switchTo("home_discover2.fxml");
    }
}
