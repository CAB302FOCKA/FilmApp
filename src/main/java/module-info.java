module com.example.filmapp {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires json.simple;
    requires okhttp3;

    opens com.example.filmapp to javafx.fxml;
    exports com.example.filmapp;
}