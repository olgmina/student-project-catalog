package com.example.editorcss.Controller;

import com.example.editorcss.HelloApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class EntranceController {
    public EntranceController(){}
    public void loadDesign(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Designer.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("");
        DesignerController main = fxmlLoader.getController();
        stage.hide();
        stage.setScene(scene);
        stage.show();
    }

    public void onProg(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Viewer.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("");
        ViewerControll main = fxmlLoader.getController();
        stage.hide();
        stage.setScene(scene);
        stage.show();
    }
}
