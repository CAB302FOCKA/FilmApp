module com.example.filmapp {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires okhttp3;
    requires json.simple;

    opens com.example.filmapp to javafx.fxml;
    exports com.example.filmapp;
}