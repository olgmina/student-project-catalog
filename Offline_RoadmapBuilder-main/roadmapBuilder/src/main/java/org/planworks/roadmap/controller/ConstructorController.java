package org.planworks.roadmap.controller;

import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import org.planworks.roadmap.MainApp;
import org.planworks.roadmap.adapters.fileWorker;
import org.planworks.roadmap.graphicElements.RoadmapObject;
import org.planworks.roadmap.selector.selectorRect;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Контроллер рабочего окна
 * @version 1.0.0
 * @author Максим Подтынников
 */
public class ConstructorController implements Initializable {

    public Pane workspace;
    public Canvas canvas;
    public ColorPicker roadColor;
    public Slider sizeRoad;
    public CheckBox selection;
    BackgroundImage background;
    RoadmapObject roadmapObject;
    selectorRect rect;
    Boolean opened=false;

    /** Инициализация
     * @param location ссылка
     * @param resources ресурсы
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        rect= new selectorRect();
        BackgroundFill myBF = new BackgroundFill(Color.rgb(108, 240, 125), new CornerRadii(1),
                new Insets(0.0,0.0,0.0,0.0));
        workspace.setBackground(new Background(myBF));
        workspace.minWidth(600);
        workspace.minHeight(300);

        canvas.autosize();
        roadmapObject = new RoadmapObject(workspace,canvas,selection.selectedProperty(),opened);
        roadColor.setOnAction(e -> roadmapObject.getRoad().setColor(roadColor.getValue()));

        sizeRoad.valueProperty().addListener(observable -> roadmapObject.getRoad().setSize(sizeRoad.getValue()));
    }

    /**
     * Сохранение изображения
     */
    public void captureAndSaveDisplay(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("png files (*.png)", "*.png"));
        File file = fileChooser.showSaveDialog(null);
        if(file != null){
            fileWorker.saveImagePNG(workspace,file);
        }
    }

    /** Начало выделения
     * @param mouseEvent событие мыши
     */
    public void onBegin(MouseEvent mouseEvent)
    {
        if(selection.isSelected() && roadmapObject.getMarks().FocusedMark==null)
            rect.onBegin(mouseEvent,workspace);
    }

    /** Выделение объектов
     * @param mouseEvent событие мыши
     */
    public void onDrag(MouseEvent mouseEvent)
    {
        if(selection.isSelected() && roadmapObject.getMarks().FocusedMark==null)
            rect.onDrag(mouseEvent,roadmapObject.getMarks(),workspace);
    }

    /** Нажатие мыши
     * @param mouseEvent событие мыши
     */
    public void mouseClicked(MouseEvent mouseEvent)
    {
        if(selection.isSelected())
            onEnd(mouseEvent);
        else addMark(mouseEvent);
    }

    /**
     * Остановка выделения
     */
    public void onStop()
    {
        if(selection.isSelected())
            rect.onStop(workspace);
    }

    /** Конец выделения
     * @param mouseEvent событие мыши
     */
    public void onEnd(MouseEvent mouseEvent)
    {
        if(selection.isSelected() && mouseEvent.getClickCount() == 2)
            rect.onEnd(roadmapObject.getMarks());
    }

    /**
     * Сохранение файла
     */
    public void saveFile()
    {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Выберите папку для сохранения...");
        fileChooser.setInitialFileName("project");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("файлы дорожной карты", "*.roadmap"));
        File file = fileChooser.showSaveDialog(workspace.getScene().getWindow());

        if (file != null) {
            fileWorker.saveFile(file,fileWorker.saveImagePNG(workspace,roadmapObject.getMarks(),canvas),roadmapObject.getMarks(), workspace.getWidth(), workspace.getHeight());
        }
    }

    /**
     * Открытие файла
     */
    public void openFile()
    {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Выбрать файл проекта");
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Дорожные карты", "*.roadmap");
        fileChooser.getExtensionFilters().add(filter);
        File file = fileChooser.showOpenDialog(MainApp.javaFXC);
        if (file!=null)
        fileWorker.loadFile(file,background,roadmapObject.getMarks(),workspace);
    }


    /**
     * Информация о программе
     */
    public void showAbout()
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Информация о программе");
        alert.setHeaderText("Разработчик: Подтынников Максим");
        alert.setContentText("Данная программа предназначена для оффлайн построения дорожных карт по разработке проекта!");
        alert.showAndWait();
    }

    /**
     * Очистка рабочего поля
     */
    public void clearAll()
    {
        roadmapObject.getRoad().reset();
        roadmapObject.getMarks().clear();
    }

    /**
     * Установка заднего фона
     */
    public void setBackground() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Выбрать изображение");
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Изображение", "*.jpg", "*.png", "*.gif", "*.bmp");
        fileChooser.getExtensionFilters().add(filter);
        File file = fileChooser.showOpenDialog(MainApp.javaFXC);
        if(file!=null) {
          setBackgroundImage(new Image("file:"+file.getAbsolutePath(), workspace.getWidth(), workspace.getHeight(), false, true));
        }
    }

    /** Установка картинки на задний фон
     * @param img изображение
     */
    public void setBackgroundImage(Image img)
    {
        background = new BackgroundImage(img,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                new BackgroundSize(1.0, 1.0, true, true, false, false));
        workspace.setBackground(new Background(background));
    }

    /** Добавление метки
     * @param mouseEvent событие мыши
     */
    public void addMark(MouseEvent mouseEvent) {
        if(mouseEvent.getClickCount() == 2)
            roadmapObject.getMarks().add(mouseEvent.getSceneX()-105,mouseEvent.getSceneY(),"");
    }
}
