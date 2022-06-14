package org.planworks.roadmap.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.planworks.roadmap.MainApp;
import org.planworks.roadmap.adapters.fileWorker;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Контроллер начального окна
 * @version 1.0.0
 * @author Максим Подтынников
 */
public class MainController {
    public Button newProjectBtn;
    public Button openPrjBtn;

    /** Открыть окно конструктора
     * @param file файл
     */
    public void constructorOpen(File file)
    {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/fxml/mainScene.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            Stage stage = new Stage();
            stage.setTitle("Конструктор дорожных карт");
            stage.setMinWidth(400);
            stage.setMinHeight(200);
            stage.setScene(scene);
            stage.show();
            ConstructorController cc=fxmlLoader.getController();
            cc.canvas.heightProperty().bind(cc.workspace.heightProperty());
            cc.canvas.widthProperty().bind(cc.workspace.widthProperty());
            if(file!=null) {
                cc.opened=true;
                fileWorker.loadFile(file,cc.background, cc.roadmapObject.getMarks(), cc.workspace);
            }
        } catch (IOException e) {
            Logger logger = Logger.getLogger(getClass().getName());
            logger.log(Level.SEVERE, "Не удалось открыть конструктор дорожных карт.", e);
        }
    }

    /**
     * Создание нового проекта
     */
    public void newProjectBtn_Click()
    {
        Stage stageTemp = (Stage)openPrjBtn.getScene().getWindow();
        constructorOpen(null);
        stageTemp.close();
    }

    /**
     * Открытие проекта
     */
    public void openPrjBtn_Click()
    {
        Stage stageTemp = (Stage)openPrjBtn.getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Выбрать файл проекта");
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Дорожные карты", "*.roadmap");
        fileChooser.getExtensionFilters().add(filter);
        File file = fileChooser.showOpenDialog(MainApp.javaFXC);
        if (file!=null)
        {
            constructorOpen(file);
        }
        stageTemp.close();
    }
}
