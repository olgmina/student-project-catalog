package com.example.editornetworkplan.Controller;

import com.example.editornetworkplan.Model.FileWorker;
import com.example.editornetworkplan.Model.PlaneNetwork;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;


import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Класс контроллер
 */
public class MainController implements Initializable {
    @FXML
    private Label welcomeText;
    @FXML
    private Pane mainPane;
    @FXML
    private Canvas canvas;
    @FXML
    private Label Mode;
    @FXML
    private ComboBox box;
    BackgroundImage background;
    int action;
    private double Y,X;
    PlaneNetwork planeNetwork;
    FileWorker fileWorker;

    /**
     * Инициализация конроллера
     * @param location URL
     * @param resources ресурсы
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mainPane.minWidth(600);
        mainPane.minHeight(400);
        canvas.autosize();
        planeNetwork = new PlaneNetwork(mainPane,canvas);
        box.setItems(FXCollections.observableArrayList("Router","Switch","PC"));
        canvas.setDisable(true);
    }

    /**
     * Функция добавления элемента
     * @param  actionEvent клик
     */
    public void onAddElement(ActionEvent actionEvent) {
        action=1;
        Mode.setText("Добавление элемента");
        canvas.setDisable(true);
    }

    /**
     *Функция удалениия элемента
     * @param  actionEvent клик
     */
    public void onAddCable(ActionEvent actionEvent) {
        action=-1;
        Mode.setText("Добавление соединения");
        canvas.setDisable(false);
        clearCombo();
    }

    /**
     *Функция перемещения элемента
     * @param actionEvent клик
     */
    public void onMoveElement(ActionEvent actionEvent) {
        action=2;
        Mode.setText("Перемещение элемента");
        clearCombo();
        canvas.setDisable(true);
    }

    /**
     *Функция удалить элемента
     * @param  actionEvent клик
     */
    public void onDeleteElement(ActionEvent actionEvent) {
        action=3;
        Mode.setText("Удаление элемента");
        clearCombo();
        canvas.setDisable(true);
    }

    /**
     *Функция удалить все элементы
     * @param actionEvent клик
     */
    public void onDeleteAllElement(ActionEvent actionEvent) {
        action=4;
        Mode.setText("Удаление всех элементов");
        clearAll();
        clearCombo();
        canvas.setDisable(true);
    }
    /**
     * Функция отслеживвние движения по Pane
     */
    private int McFocus()
    {
        if(planeNetwork.getElementAdapter().getElementList().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error ");
            alert.setHeaderText("Схема сети без элементов");
            alert.showAndWait();
        }else {
            for (int i = planeNetwork.getElementAdapter().getElementList().size() - 1; i >= 0; i--) {
                var t = planeNetwork.getElementAdapter().getElementList().get(i);
                if (X > t.getX() && X < t.getX() + 50 && Y > t.getY() && Y < t.getY() + 50) {
                    int index = planeNetwork.getElementAdapter().getElementList().indexOf(t);
                    System.out.println("index=" + index);
                    return index;
                }
            }
        }
        return -1;
    }

    /**
     * Функция отслеживвние движения по Pane
     * @param mouseEvent клик
     */
    public void onMm(MouseEvent mouseEvent) {
        X=mouseEvent.getX();
        Y=mouseEvent.getY();
    }

    /**
     * Удалить combobox
     */
    public void clearCombo(){
        box.getSelectionModel().clearSelection();
    }

    /**
     * ФУНКЦИЯ лика по Pane
     * @param mouseEvent клик
     */
    public void onMClick(MouseEvent mouseEvent) {
        switch (action)
        {
            case 1:
                if(box.getSelectionModel().getSelectedIndex()==-1){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error ");
                    alert.setHeaderText("Вы не выбрали элемент");
                    alert.showAndWait();
                }
                planeNetwork.getElementAdapter().add(mouseEvent.getX(), mouseEvent.getY(), box.getSelectionModel().getSelectedIndex());
                break;
            case 2:

                if(McFocus()!=-1){
                    planeNetwork.getElementAdapter().getElementList().get(McFocus()).move(mouseEvent.getX(), mouseEvent.getY());
                }
                break;
            case 3:
                    if (McFocus() != -1) {
                        planeNetwork.getElementAdapter().delete(planeNetwork.getElementAdapter().get(McFocus()));
                    }
                break;
        }

    }
    /**
     * отчистака рабочей области
     */
    public void clearAll()
    {
        planeNetwork.getCable().reset();
        planeNetwork.getElementAdapter().clear();
    }

    /**
     * Сохранение файла
     */
    public void SaveFaile(){
        fileWorker = new FileWorker();
        fileWorker.Save((ArrayList)planeNetwork.getElementAdapter().getElementList());
    }
    /**
     * About
     * @param actionEvent клик
     */
    public void showAbout(ActionEvent actionEvent) {
    }
    /**
     * Загрузка  файла
     * @param actionEvent клик
     */
    public void onLoad(ActionEvent actionEvent) {
       fileWorker = new FileWorker();
       planeNetwork.setElementAdapter(fileWorker.Load(mainPane));

    }
    /**
     * Экспорт в Png
     * @param actionEvent клик
     */
    public void onSavepng(ActionEvent actionEvent) {
        fileWorker = new FileWorker();
        fileWorker.saveImagePNG(mainPane);
    }

    /**
     * Загрузка заднего фона
     * @param actionEvent клик
     */
    public void onloadBg(ActionEvent actionEvent) {
        fileWorker = new FileWorker();
        fileWorker.OpenImg(mainPane);
    }
}