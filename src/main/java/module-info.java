module com.example.filmapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.kordamp.bootstrapfx.core;
    requires json.simple;
    requires okhttp3;
    requires java.sql;
    requires java.desktop;
    requires jbcrypt;

    opens com.example.filmapp to javafx.fxml;
    exports com.example.filmapp;
    exports com.example.filmapp.state;
    opens com.example.filmapp.state to javafx.fxml;
    exports com.example.filmapp.factory;
    opens com.example.filmapp.factory to javafx.fxml;
    exports com.example.filmapp.util;
    opens com.example.filmapp.util to javafx.fxml;
    exports com.example.filmapp.service;
    opens com.example.filmapp.service to javafx.fxml;
    exports com.example.filmapp.controller;
    opens com.example.filmapp.controller to javafx.fxml;
    exports com.example.filmapp.model;
    opens com.example.filmapp.model to javafx.fxml;
}