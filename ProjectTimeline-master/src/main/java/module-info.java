module com.example.projecttimeline {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;

    opens com.example.projecttimeline to javafx.fxml;
    exports com.example.projecttimeline;
    exports UI;
    opens UI to javafx.fxml;
}