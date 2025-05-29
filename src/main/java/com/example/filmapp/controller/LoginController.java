package com.example.filmapp.controller;

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

        if (email.isEmpty() || password.isEmpty()) {
            loginStatus.setText("Please fill in all fields.");
            return;
        }

        loginStatus.setText("Logging in..");

        Task<Boolean> logintask = new Task<>() {
            private String userID;
            private String username;
            private String userEmail;
            private String hashedPassword;

            @Override
            protected Boolean call() {
                String query = "SELECT userID, userName, userEmail, userPass FROM user WHERE userEmail = ?";

                try (Connection connection = DatabaseConnection.getConnection();
                     PreparedStatement preparedStatement = connection.prepareStatement(query)) {

                    preparedStatement.setString(1, email);
                    ResultSet resultSet = preparedStatement.executeQuery();

                    if (resultSet.next()) {
                        String storedHashPassword = resultSet.getString("userPass");

                        if (BCrypt.checkpw(password, storedHashPassword)) {
                            userID = resultSet.getString("userID");
                            username = resultSet.getString("userName");
                            userEmail = resultSet.getString("userEmail");
                            hashedPassword = storedHashPassword;
                            return true;
                        }
                    }
                } catch (SQLException e) {
                    updateMessage("Database Error");
                    e.printStackTrace();
                }
                return false;
            }


            protected void succeeded() {
                boolean loginSuccess = getValue();
                if (loginSuccess) {
                    AppState.getInstance().setCurrentUserId(userID);
                    UserSession.setUser(username, userEmail, hashedPassword);
                    loginStatus.setText("Login Successful!");
                    try {
                        SceneManager.switchTo("home_discover2.fxml");
                    }catch (IOException e){
                        e.printStackTrace();
                        loginStatus.setText("Fail to switch scene");
                    }
                }else {
                    loginStatus.setText("Login Failed!");
                }
            }

            @Override
            protected void failed() {
                loginStatus.setText("Login Failed!");
            }
        };

        new Thread(logintask).start();
    }
}
