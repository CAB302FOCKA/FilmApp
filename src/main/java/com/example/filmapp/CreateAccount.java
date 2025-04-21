package com.example.filmapp;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.sql.*;

public class CreateAccount extends Application {

    @FXML
    private TextField myUsername;
    @FXML
    private TextField myEmail;
    @FXML
    private TextField myPass;
    @FXML
    private TextField myConfPass;
    @FXML
    private Button myCreateAccount;
    @FXML
    private Button myBack;
    @FXML
    private Label myLogin;

    String username;
    String email;
    String pass;
    String confPass;

    // Method triggered when the submit button is pressed
    public void submit(ActionEvent event) throws IOException {
        username = myUsername.getText();
        email = myEmail.getText();
        pass = myPass.getText();
        confPass = myConfPass.getText();

        // Print the values for debugging
        System.out.println(username + " " + email + " " + pass + " " + confPass);

        // Validate inputs and proceed to create the account
        if (validateInputs()) {
            createAccount();
        }
    }

    // Method to validate the user inputs
    private boolean validateInputs() {
        if (username.isEmpty() || email.isEmpty() || pass.isEmpty() || confPass.isEmpty()) {
            System.out.println("All fields are required.");
            return false;
        }

        // Check if password and confirmation match
        if (!pass.equals(confPass)) {
            System.out.println("Passwords do not match.");
            return false;
        }
        // Check to see if email follows format email@emailprovider.com
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";
        if (!email.matches(emailRegex)) {
            System.out.println("Invalid email format.");
            return false;
        }
        return true;
    }

    // Method to create the account by storing hashed password in the database
    public void createAccount() {
        String hashedPassword = hashPassword(pass);

        // Use the hashed password in your SQL insert query
        String query = "INSERT INTO user (userName, userEmail, userPass) VALUES (?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, username);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, hashedPassword);  // Store the hashed password

            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println("Account created, rows affected: " + rowsAffected);
        } catch (SQLException e) {
            System.err.println("Error creating account: " + e.getMessage());
        }
    }

    // Method to hash the password using BCrypt
    private String hashPassword(String password) {
        String salt = BCrypt.gensalt(12);
        return BCrypt.hashpw(password, salt);  // Hash the password
    }

    // JavaFX application setup
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Create-Account.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1920, 1080);
        stage.setScene(scene);
        stage.show();
    }

    // Main method to launch the JavaFX application
    public static void main(String[] args) {
        launch();
    }
}
