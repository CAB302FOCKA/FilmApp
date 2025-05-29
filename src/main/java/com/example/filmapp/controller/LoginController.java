package com.example.filmapp.controller;

import com.example.filmapp.service.AccountService;
import com.example.filmapp.service.DatabaseConnection;
import com.example.filmapp.state.AppState;
import com.example.filmapp.util.SceneManager;
import com.example.filmapp.UserSession;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Function to log users in. The function queries the database based off form submitted and compares the userEmail with the hashed userPass
 */
public class LoginController {

    @FXML
    public TextField emailField;

    @FXML
    public TextField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    public Label loginStatus;

    @FXML
    private Label accCreate;

    private final AccountService loginService = new AccountService();

    @FXML
    public void initialize() {
        System.out.println("Login Controller initialized!");
    }

    @FXML
    private void handleCreateAccountRedirect(MouseEvent event) throws IOException {
        SceneManager.switchTo("create-account.fxml");
    }

    @FXML
    public void handleLogin(ActionEvent event) {
        String email = emailField.getText();
        String password = passwordField.getText();

        loginStatus.setText("Logging in...");

        loginService.login(email, password,
                () -> loginStatus.setText("Please fill in all fields."),
                () -> loginStatus.setText("Login Failed."),
                () -> loginStatus.setText("Login Successful!") );
    }
}
