package com.example.filmapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginController {

    @FXML
    private TextField emailField;

    @FXML
    private TextField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    private Label loginStatus;

    @FXML
    private void handleLogin(ActionEvent event) {
        String email = emailField.getText();
        String password = passwordField.getText();

        if (email.isEmpty() || password.isEmpty()) {
            loginStatus.setText("Please fill in all fields.");
            return;
        }

        String query = "SELECT password FROM users WHERE email = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String storedHashedPassword = resultSet.getString("password");

                if (BCrypt.checkpw(password, storedHashedPassword)) {
                    loginStatus.setText("Login successful!");
                    // TODO: Switch to dashboard/home scene
                } else {
                    loginStatus.setText("Incorrect password.");
                }
            } else {
                loginStatus.setText("Email not found.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            loginStatus.setText("Database error.");
        }
    }
}
