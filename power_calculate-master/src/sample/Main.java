package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader();
        ScrollPane p = fxmlLoader.load(getClass().getResource("sample.fxml").openStream());
        primaryStage.setTitle("Калькулятор энергопотребления");
        Scene scene = new Scene(p, 800, 800);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


   public static void main(String[] args) {
        launch(args);
    }
}
