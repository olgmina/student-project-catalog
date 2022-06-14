package UI;

import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;

/** Класс по созданию линии на дорожной карте   */
public class NodeLineConnectionBuilder {

    AnchorPane canvasAnchorPane;

    /** Конструктор принимают AnchorPane, на котором будет рисоваться линии */
    public NodeLineConnectionBuilder (AnchorPane canvasAnchorPane) {

        this.canvasAnchorPane = canvasAnchorPane;
    }

    /** Метод возвращает созданную линию на заданном в конструкторе AnchorPane */
    public Line create () {

        Line line = new Line();

        canvasAnchorPane.getChildren().add(line);
        line.toBack();

        return line;
    }
}
