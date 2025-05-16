package com.example.filmapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.TextArea;

import java.io.IOException;

public class PrivacyDataController {

    @FXML
    private TextArea privacyTextArea;

    @FXML
    public void initialize() {
        String privacyText = "Privacy & Data Policy for FOCKA FILMS\n\n" +
                "Effective Date: April 30, 2025\n\n" +
                "This Privacy Policy outlines how FOCKA FILMS Pty Ltd (\"we\", \"us\", or \"our\") collects, uses, discloses, and protects your information when you use our movie discovery platform.\n\n" +
                "Company Address:\n" +
                "FOCKA FILMS Pty Ltd\n" +
                "100 Film Street, West End\n" +
                "Brisbane, QLD 4101\n" +
                "Australia\n\n" +
                "Contact:\n" +
                "Phone: +61 7 3123 4567\n" +
                "Email: support@fockafilms.com\n\n" +
                "1. Information We Collect\n" +
                "- Email address and basic login details for authentication.\n" +
                "- User activity (e.g., search history, clicks) to improve recommendations.\n" +
                "- Device data (e.g., IP address, browser version) for diagnostics.\n\n" +
                "2. Use of Information\n" +
                "- Provide access to app features and functionality.\n" +
                "- Improve platform performance and user experience.\n" +
                "- Respond to user inquiries and provide support.\n\n" +
                "3. Data Storage and Security\n" +
                "- All user data is stored securely and encrypted where applicable.\n" +
                "- We take reasonable measures to protect your information from loss, misuse, or unauthorized access.\n\n" +
                "4. Third-Party APIs\n" +
                "- We may use third-party APIs (e.g., movie data sources). We do not control their privacy practices.\n" +
                "- No personal data is shared with these APIs.\n\n" +
                "5. User Control\n" +
                "- You may request data deletion by contacting support@fockafilms.com.\n" +
                "- You can manage your login credentials and activity settings through your account.\n\n" +
                "6. Children’s Privacy\n" +
                "- The application is not intended for users under the age of 13.\n\n" +
                "7. Changes\n" +
                "- We may update this Privacy Policy. Continued use constitutes acceptance of any changes.\n\n" +
                "8. Governing Law\n" +
                "This Privacy Policy is governed by the laws of Queensland, Australia.\n\n" +
                "For questions or concerns, contact us at support@fockafilms.com.\n\n" +
                "© 2025 FOCKA FILMS Pty Ltd. All rights reserved.";

        privacyTextArea.setText(privacyText);
    }

    @FXML
    private void handleBack(ActionEvent event) throws IOException {
        Parent settingsPage = FXMLLoader.load(getClass().getResource("/com/example/filmapp/settings.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(settingsPage, 1920, 1080));
        stage.show();
    }
}
