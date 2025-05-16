package com.example.filmapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;  // <-- This import is required
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.TextArea;


import java.io.IOException;

public class TermsAndConditionsController {

    @FXML
    private TextArea termsTextArea;

    @FXML
    public void initialize() {
        // Set the text content for Terms and Conditions
        String termsText = "Welcome to FOCKA FILMS\n\n" +
                "Effective Date: April 30, 2025\n\n" +
                "These Terms and Conditions (\"Terms\") govern your use of the FOCKA FILMS movie discovery platform (the \"Application\") provided by FOCKA FILMS Pty Ltd (\"we\", \"us\", or \"our\"), based in Brisbane, QLD.\n\n" +
                "Company Address:\n" +
                "FOCKA FILMS Pty Ltd\n" +
                "100 Film Street, West End\n" +
                "Brisbane, QLD 4101\n" +
                "Australia\n\n" +
                "Contact:\n" +
                "Phone: +61 7 3123 4567\n" +
                "Email: support@fockafilms.com\n\n" +
                "1. Acceptance\n" +
                "By using this application, you agree to be bound by these Terms.\n\n" +
                "2. Use of the Service\n" +
                "FOCKA FILMS provides a platform for users to search for movies, view movie data, cast, and related information. This is a discovery tool only and does not stream or host any movies.\n\n" +
                "3. User Conduct\n" +
                "Users must not misuse the platform, attempt to breach security, or reverse engineer any part of the system.\n\n" +
                "4. Intellectual Property\n" +
                "All content, trademarks, and graphics used in this Application are owned by FOCKA FILMS or third-party providers and protected under applicable intellectual property laws.\n\n" +
                "5. Third-Party Services\n" +
                "We may include links to external services or APIs. We are not responsible for their content or policies.\n\n" +
                "6. Termination\n" +
                "We reserve the right to terminate your access to the application at our discretion without notice.\n\n" +
                "7. Limitation of Liability\n" +
                "FOCKA FILMS will not be liable for any indirect or consequential damages arising from your use of the Application.\n\n" +
                "8. Changes\n" +
                "These Terms may be updated occasionally. Your continued use of the app constitutes acceptance of the new terms.\n\n" +
                "9. Governing Law\n" +
                "These terms are governed by the laws of Queensland, Australia.\n\n" +
                "For full details, contact us at support@fockafilms.com.\n\n" +
                "© 2025 FOCKA FILMS Pty Ltd. All rights reserved.";

        // Set the content of the TextArea
        termsTextArea.setText(termsText);
    }

    @FXML
    private void handleBack(ActionEvent event) throws IOException {
        Parent settingsPage = FXMLLoader.load(getClass().getResource("/com/example/filmapp/settings.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(settingsPage, 1920, 1080));
        stage.show();
    }
}
