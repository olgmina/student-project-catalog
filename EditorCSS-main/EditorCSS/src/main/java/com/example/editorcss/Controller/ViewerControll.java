package com.example.editorcss.Controller;

import com.example.editorcss.Model.FileWorker;
import com.example.editorcss.Model.StyleCode;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;

import java.io.File;

public class ViewerControll {
    @FXML
    private TextArea textCode;
    private StyleCode styleCode;
    private FileWorker worker;
    public void Open(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("CSS files (*.css)", "*.css");
        fileChooser.setTitle("Open style file");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showOpenDialog(null);
        if(file == null){
            return;
        }
        styleCode = new StyleCode();
        worker=new FileWorker();
//        model = new com.example.editorcss.Model.FileCss();
        System.out.println(file.getAbsoluteFile());
        String filepath = file.getAbsolutePath();
        String filename = file.getName();
        //gridCss.getStylesheets().add("file:///" + filepath.replace('\\', '/'));
        worker.openFile(filepath,styleCode);
        textCode.setText(styleCode.createCssAllText().toString());
    }
    public void Save(ActionEvent actionEvent) {
        worker.saveFile(textCode.getText());
    }
}
