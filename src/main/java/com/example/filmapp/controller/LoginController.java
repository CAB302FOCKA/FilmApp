package com.example.filmapp.controller;

import com.example.filmapp.service.DatabaseConnection;
import com.example.filmapp.state.AppState;
import com.example.filmapp.util.SceneManager;
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
    private TextField emailField;

    @FXML
    private TextField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    private Label loginStatus;

    @FXML
    private Label accCreate;

    @FXML
    public void initialize() {
        System.out.println("LoginController initialized!");
    }

    @FXML
    private void handleCreateAccountRedirect(MouseEvent event) throws IOException {
        SceneManager.switchTo("create-account.fxml");
    }

    @FXML
    private void handleLogin(ActionEvent event) {
        String email = emailField.getText();
        String password = passwordField.getText();

        if (email.isEmpty() || password.isEmpty()) {
            loginStatus.setText("Please fill in all fields.");
            return;
        }

        String query = "SELECT userID, userPass FROM user WHERE userEmail = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String storedHashedPassword = resultSet.getString("userPass");

                if (BCrypt.checkpw(password, storedHashedPassword)) {
                    String userIdFromDatabase = resultSet.getString("userID");
                    AppState.getInstance().setCurrentUserId(userIdFromDatabase);

                    loginStatus.setText("Login successful!");
                    System.out.println("Logged in as user ID: " + userIdFromDatabase);
                    SceneManager.switchTo("home_discover2.fxml");
                } else {
                    loginStatus.setText("Incorrect password.");
                }
            } else {
                loginStatus.setText("Email not found.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            loginStatus.setText("Database error.");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
