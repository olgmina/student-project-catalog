package com.example.editorcss;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Класс для запуска приложения
 */
public class HelloApplication extends Application {
    /**
     * Функция отображения окна
     * @param stage Stage
     * @throws IOException ощибка
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Entrance.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 300, 300);
        stage.setTitle("Редактор CSS-стиля для fxml-форм");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Главная функция
     * @param args String[]
     */
    public static void main(String[] args) {
        launch();
    }
}