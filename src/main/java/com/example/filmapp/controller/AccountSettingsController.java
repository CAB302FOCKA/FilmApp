package com.example.filmapp.controller;

import com.example.filmapp.UserSession;
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

/**
 * Class for the account settings. Assists users with their account settings
 */

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
        Parent settingsPage = FXMLLoader.load(getClass().getResource("home_discover2.fxml"));
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
    @FXML
    private void handleDeleteAccount(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Account Deletion");
        alert.setHeaderText("Are you sure you want to delete your account?");
        alert.setContentText("This action cannot be undone.");

        // Style the alert
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.setStyle("-fx-background-color: #fef2f2;");
        dialogPane.lookup(".header-panel").setStyle("-fx-background-color: #fee2e2;");
        dialogPane.lookup(".content.label").setStyle("-fx-text-fill: #b91c1c; -fx-font-weight: bold;");

        ButtonType yesButton = new ButtonType("Yes", ButtonBar.ButtonData.YES);
        ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.NO);
        alert.getButtonTypes().setAll(yesButton, noButton);

        alert.showAndWait().ifPresent(type -> {
            if (type == yesButton) {
                deleteAccountAndLogout(event);
            }
        });
    }

    private void deleteAccountAndLogout(ActionEvent event) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String deleteSQL = "DELETE FROM user WHERE userEmail = ?";
            try (PreparedStatement stmt = conn.prepareStatement(deleteSQL)) {
                stmt.setString(1, currentEmail);
                stmt.executeUpdate();
            }

            UserSession.clearSession();

            // Redirect to login screen
            Parent loginPage = FXMLLoader.load(getClass().getResource("/com/example/filmapp/login.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(loginPage, 1920, 1080));
            stage.show();

        } catch (Exception e) {
            showError("Error deleting account: " + e.getMessage());
            e.printStackTrace();
        }
    }
    private void showSuccess(String message) {
        statusLabel.setStyle("-fx-text-fill: green;");
        statusLabel.setText(message);
    }
}
