package com.example.filmapp;

import com.example.filmapp.service.DatabaseConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AccountSettingsController {

    @FXML private Label emailLabel;
    @FXML private Label usernameLabel;
    @FXML private TextField newEmailField;
    @FXML private PasswordField newPasswordField;
    @FXML private PasswordField confirmPasswordField;
    @FXML private CheckBox showPasswordCheckBox;
    @FXML private Label statusLabel;

    private String currentEmail;
    private String currentPassword;

    @FXML
    private void initialize() {
        currentEmail = UserSession.getEmail();
        currentPassword = UserSession.getHashedPassword();
        usernameLabel.setText(UserSession.getUsername());
        emailLabel.setText(currentEmail != null ? currentEmail : "No user logged in");
        statusLabel.setText("");
    }

    @FXML
    private void handleBack(ActionEvent event) throws IOException {
        Parent settingsPage = FXMLLoader.load(getClass().getResource("/com/example/filmapp/settings.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(settingsPage, 1920, 1080));
        stage.show();
    }

    @FXML
    private void handleSaveChanges(ActionEvent event) {
        String newEmail = newEmailField.getText().trim();
        String newPassword = newPasswordField.getText();
        String confirmPassword = confirmPasswordField.getText();
        String currentUsername = UserSession.getUsername();

        boolean updateEmail = false, updatePassword = false;

        if (!newEmail.isEmpty()) {
            if (!isValidEmail(newEmail)) {
                showError("Invalid email. Must include '@' and end with '.com'.");
                return;
            }
            updateEmail = true;
        }

        if (!newPassword.isEmpty()) {
            if (!newPassword.equals(confirmPassword)) {
                showError("Passwords do not match.");
                return;
            }
            updatePassword = true;
        }

        if (!updateEmail && !updatePassword) {
            showError("Please enter a new email or password to update.");
            return;
        }

        try (Connection connection = DatabaseConnection.getConnection()) {
            if (updateEmail) {
                String updateEmailSQL = "UPDATE user SET userEmail = ? WHERE userEmail = ?";
                try (PreparedStatement stmt = connection.prepareStatement(updateEmailSQL)) {
                    stmt.setString(1, newEmail);
                    stmt.setString(2, currentEmail);
                    stmt.executeUpdate();
                    currentEmail = newEmail;
                    emailLabel.setText(newEmail);
                }
            }

            if (updatePassword) {
                String hashedNewPass = BCrypt.hashpw(newPassword, BCrypt.gensalt(12));
                String updatePassSQL = "UPDATE user SET userPass = ? WHERE userEmail = ?";
                try (PreparedStatement stmt = connection.prepareStatement(updatePassSQL)) {
                    stmt.setString(1, hashedNewPass);
                    stmt.setString(2, currentEmail); // use current (possibly updated) email
                    stmt.executeUpdate();
                    currentPassword = hashedNewPass;
                }
            }

            // Update session
            UserSession.setUser(currentUsername, currentEmail, currentPassword);
            showSuccess("Account updated successfully.");
            clearFields();

        } catch (SQLException e) {
            e.printStackTrace();
            showError("Database error: " + e.getMessage());
        }
    }

    @FXML
    private void togglePasswordVisibility() {
        boolean show = showPasswordCheckBox.isSelected();

        if (show) {
            newPasswordField.setPromptText(newPasswordField.getText());
            confirmPasswordField.setPromptText(confirmPasswordField.getText());
            newPasswordField.setText("");
            confirmPasswordField.setText("");
            newPasswordField.setDisable(true);
            confirmPasswordField.setDisable(true);
        } else {
            newPasswordField.setDisable(false);
            confirmPasswordField.setDisable(false);
            newPasswordField.setText(newPasswordField.getPromptText());
            confirmPasswordField.setText(confirmPasswordField.getPromptText());
            newPasswordField.setPromptText("Enter new password");
            confirmPasswordField.setPromptText("Confirm new password");
        }
    }

    private void clearFields() {
        newEmailField.clear();
        newPasswordField.clear();
        confirmPasswordField.clear();
        showPasswordCheckBox.setSelected(false);
        newPasswordField.setDisable(false);
        confirmPasswordField.setDisable(false);
        newPasswordField.setPromptText("Enter new password");
        confirmPasswordField.setPromptText("Confirm new password");
    }

    private boolean isValidEmail(String email) {
        return email.matches("^[^@\\s]+@[^@\\s]+\\.com$");
    }

    private void showError(String message) {
        statusLabel.setStyle("-fx-text-fill: red;");
        statusLabel.setText(message);
    }

    private void showSuccess(String message) {
        statusLabel.setStyle("-fx-text-fill: green;");
        statusLabel.setText(message);
    }
}
