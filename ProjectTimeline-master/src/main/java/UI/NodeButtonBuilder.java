package UI;

import ProjectTimeLine.TimeLine.Node;
import ProjectTimeLine.TimeLine.UINode;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;

/** Класс создает кнопку с параметрами нода, переданного в конструкторе */
public class NodeButtonBuilder {

    Node node;

    public NodeButtonBuilder (Node node) {
        this.node = node;
    }

    /** Возвражает кнопку с параметрами нода, переданного в конструкторе  */
    public Button create() {

        Button button = new Button();
        UINode uiNode = new UINode();

        uiNode.radius = 100;
        uiNode.color = Color.web("#1ABC9C");

        if(node.uiNode != null){
            Color color = uiNode.color;
            uiNode = node.uiNode;
            if(node.uiNode.color == null)
                node.uiNode.color = color;
        }
        else {
            node.uiNode = uiNode;
        }

        button.setStyle(
                "-fx-background-color: rgb(" + uiNode.color.getRed() * 255 + "," + uiNode.color.getGreen() * 255 + ", " + uiNode.color.getBlue() * 255 + "); " +
                        "-fx-cursor: pointer; " +
                        "-fx-background-radius: 500px; "
        );

        button.setMinSize(10, 10);
        button.setPrefSize(uiNode.radius, uiNode.radius);
        button.setMaxSize(uiNode.radius, uiNode.radius);

        button.setText(node.name);

        return button;
    }
}
