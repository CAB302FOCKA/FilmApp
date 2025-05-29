package com.example.filmapp.controller;

import com.example.filmapp.service.AccountService;
import com.example.filmapp.service.DatabaseConnection;
import com.example.filmapp.util.SceneManager;
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

/**
 * Class to create user account. Pulls userName, userEmail and userPass and uploads it to database.
 * Before uploading to the database the userPass is hashed to ensure user security
 */
public class CreateAccountController {

    @FXML
    public TextField myUsername;
    @FXML
    public TextField myEmail;
    @FXML
    public TextField myPass;
    @FXML
    public TextField myConfPass;
    @FXML
    private Button myCreateAccount;
    @FXML
    private Button myBack;
    @FXML
    private Label myLogin;

    public String username;
    public String email;
    public String pass;
    public String confPass;

    private final AccountService accountService = new AccountService();

    @FXML
    private void submit(ActionEvent event) throws IOException {
        username = myUsername.getText();
        email = myEmail.getText();
        pass = myPass.getText();
        confPass = myConfPass.getText();

        accountService.register(
                username,
                email,
                pass,
                confPass,

                // onEmptyFields
                () -> myLogin.setText("All fields are required."),

                // onPasswordMismatch
                () -> myLogin.setText("Passwords do not match."),

                // onInvalidEmail
                (msg) -> myLogin.setText(msg),

                // onResult (success or failure)
                (msg) -> myLogin.setText(msg)
        );
    }

    @FXML
    private void handleLoginRedirect(MouseEvent event) throws IOException {
        SceneManager.switchTo("/com/example/filmapp/login.fxml");
    }
}
