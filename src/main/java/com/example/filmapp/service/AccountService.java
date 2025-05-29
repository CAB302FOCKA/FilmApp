package com.example.filmapp.service;

import com.example.filmapp.UserSession;
import com.example.filmapp.state.AppState;
import com.example.filmapp.util.SceneManager;
import javafx.concurrent.Task;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.Consumer;

/**
 * Service that manages login and register
 */
public class AccountService {

    // ========== VALIDATION METHODS ==========

    public boolean isAnyFieldEmpty(String... fields) {
        for (String f : fields) {
            if (f == null || f.isBlank()) return true;
        }
        return false;
    }

    public boolean doPasswordsMatch(String pass, String confirmPass) {
        return pass.equals(confirmPass);
    }

    public boolean isValidEmailFormat(String email) {
        return email != null && email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$");
    }

    // ========== PASSWORD ==========

    public String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(12));
    }

    public boolean verifyPassword(String password, String hashed) {
        return BCrypt.checkpw(password, hashed);
    }

    // ========== REGISTER ==========

    public void register(String username, String email, String pass, String confirmPass,
                           Runnable onEmptyFields,
                           Runnable onPasswordMismatch,
                         Consumer<String> onInvalidEmail,
                         Consumer<String> onResult) {
        if (isAnyFieldEmpty(username, email, pass, confirmPass)) {
            onEmptyFields.run();
            return;
        }

        if (!doPasswordsMatch(pass,confirmPass)) {
            onPasswordMismatch.run();
            return;
        }

        if (isValidEmailFormat(email)) {
            onInvalidEmail.accept("Invalid email format.");
            return;
        }

        Task<Boolean> registerTask = new Task<>() {
            @Override
            protected Boolean call() {
                String hashedPassword = hashPassword(pass);
                String query = "INSERT INTO user (userName, userEmail, userPass) VALUES (?, ?, ?)";

                try (Connection connection = DatabaseConnection.getConnection();
                     PreparedStatement statement = connection.prepareStatement(query)) {

                    statement.setString(1, username);
                    statement.setString(2, email);
                    statement.setString(3, hashedPassword);

                    return statement.executeUpdate() > 0;

                } catch (SQLException e) {
                    e.printStackTrace();
                    return false;
                }
            }

            @Override
            protected void succeeded() {
                boolean created = getValue();
                if (created) {
                    onResult.accept("Account created successfully.");
                    try {
                        SceneManager.switchTo("login.fxml");
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                } else {
                    onResult.accept("Failed to create account.");
                }
            }

            @Override
            protected void failed() {
                onResult.accept("Error during account creation.");
            }
        };

        new Thread(registerTask).start();
    }

    public void login(String email, String password, Runnable onEmptyFields, Runnable onFailure, Runnable onSuccess) {
        if (email.isEmpty() || password.isEmpty()) {
            onEmptyFields.run();
            return;
        }

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

                        if (verifyPassword(password, storedHashPassword)) {
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

            @Override
            protected void succeeded() {
                boolean loginSuccess = getValue();
                if (loginSuccess) {
                    AppState.getInstance().setCurrentUserId(userID);
                    UserSession.setUser(username, userEmail, hashedPassword);
                    onSuccess.run();
                    try {
                        SceneManager.switchTo("home_discover2.fxml");
                    }catch (IOException e){
                        e.printStackTrace();
                        onFailure.run();
                    }
                }else {
                    onFailure.run();
                }
            }

            @Override
            protected void failed() {
                onFailure.run();
            }
        };

        new Thread(logintask).start();
    }
}