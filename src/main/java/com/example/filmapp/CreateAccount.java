package com.example.filmapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CreateAccount {

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

    private String username;
    private String email;
    private String pass;
    private String confPass;

    @FXML
    private void submit(ActionEvent event) throws IOException {
        username = myUsername.getText();
        email = myEmail.getText();
        pass = myPass.getText();
        confPass = myConfPass.getText();

        if (validateInputs()) {
            if (createAccount()) {
                switchToLoginPage(); // redirect if account successfully created
            }
        }
    }

    @FXML
    private void handleLoginRedirect(MouseEvent event) throws IOException {
        SceneManager.switchTo("login.fxml");
    }

    private boolean validateInputs() {
        if (username.isEmpty() || email.isEmpty() || pass.isEmpty() || confPass.isEmpty()) {
            System.out.println("All fields are required.");
            return false;
        }

        if (!pass.equals(confPass)) {
            System.out.println("Passwords do not match.");
            return false;
        }

        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";
        if (!email.matches(emailRegex)) {
            System.out.println("Invalid email format.");
            return false;
        }

        return true;
    }

    private boolean createAccount() {
        String hashedPassword = hashPassword(pass);
        String query = "INSERT INTO user (userName, userEmail, userPass) VALUES (?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, username);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, hashedPassword);

            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println("Account created successfully.");
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error creating account: " + e.getMessage());
            return false;
        }
    }

    private String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(12));
    }

    private void switchToLoginPage() throws IOException {
        Stage stage = (Stage) myCreateAccount.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
        Scene scene = new Scene(loader.load(), 1920, 1080);
        stage.setScene(scene);
    }
}
