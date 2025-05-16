package com.example.filmapp.controller;

import com.example.filmapp.util.SceneManager;
import javafx.fxml.FXML;


import java.io.IOException;

public class SettingsController {

    @FXML
    protected void handlePrivacy() {
        System.out.println("Privacy & Data clicked.");
        try {
            SceneManager.switchTo("privacy_data.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void handleTerms() {
        System.out.println("Terms and Conditions clicked.");
        try{
            SceneManager.switchTo("terms_and_conditions.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void handleLogout() {
        System.out.println("Logging out...");
        try{
            SceneManager.switchTo("login.fxml");
        } catch(IOException e){
            e.printStackTrace();
        }

    }
    @FXML
    private void handleBackController() throws IOException {
        SceneManager.switchTo("home_discover2.fxml");
    }

    @FXML
    private void handleSettingsButton() {
        try {
            SceneManager.switchTo("settings.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
