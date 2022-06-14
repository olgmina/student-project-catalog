module com.example.editorcss {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;

    opens com.example.editorcss to javafx.fxml;
    exports com.example.editorcss;
    exports com.example.editorcss.Controller;
    opens com.example.editorcss.Controller to javafx.fxml;
}