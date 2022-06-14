module com.example.editornetworkplan {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires java.desktop;
    requires javafx.media;
    requires javafx.swing;

    opens com.example.editornetworkplan to javafx.fxml;
    exports com.example.editornetworkplan;
    exports com.example.editornetworkplan.Controller;
    opens com.example.editornetworkplan.Controller to javafx.fxml;
}