package org.planworks.roadmap;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.planworks.roadmap.controller.MainController;

import java.io.File;

/**
 * @version 1.0.0
 * @author Максим Подтынников
 */
public class MainApp extends Application {

    public static Stage javaFXC;
    private static String filePath;

    /** Начало работы
     * @param stage уровень
     * @throws Exception исключение
     */
    @Override
    public void start(Stage stage) throws Exception {
        if(filePath!=null)
        {
            MainController mainController=new MainController();
            mainController.constructorOpen(new File(filePath));
        }
        else {
            String fxmlFile = "/fxml/hello.fxml";
            FXMLLoader loader = new FXMLLoader();
            Parent root = loader.load(getClass().getResourceAsStream(fxmlFile));
            stage.setTitle("Стартовое меню");
            stage.setResizable(false);
            stage.getIcons().add(new Image("file:imgs/favicon_156.png", 128, 128, false, false));
            stage.setScene(new Scene(root, root.prefWidth(420), root.prefHeight(200)));
            stage.show();
            javaFXC = stage;
        }
    }

    /** Главный метод
     * @param args аргументы
     */
    public static void main(String[] args) {
        if(args.length>0)
            filePath=args[0];
        launch(args);
    }
}
