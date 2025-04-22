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
}
