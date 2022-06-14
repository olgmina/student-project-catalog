package UI;

import ProjectTimeLine.TimeLine.Node;
import Tool.ITool;
import Tool.MovedNodeTool;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

import java.util.ArrayList;
import java.util.List;

/** Класс-контроллер нода, обрабатывает UI нода */
public class NodeButtonControl {

    /** Массив содержит ссылку на родителя */
    private NodeButtonControl parent;
    private Node node;
    private Button button;
    private ITool currentTool;
    private Line lineToParent;

    /** Массив содержит ссылки на дочерние элементы нода */
    public List<NodeButtonControl> childButtonControls;

    private boolean isChildShow;
    private boolean isMoved;

    public NodeButtonControl(Node node, Button button, ITool currentTool, AnchorPane canvasAnchorPane, InspectorNode inspectorNode, Label infoLabel) {

        this.node = node;
        this.button = button;
        this.currentTool = currentTool;

        childButtonControls = new ArrayList<NodeButtonControl>();

        button.setOnDragDetected(mouseEvent -> {

            if(this.currentTool != null)
                this.currentTool.end();

            if (!inspectorNode.getIsActive() || inspectorNode.getNodeButtonControl() != this){
                return;
            }

            this.currentTool = new MovedNodeTool(this, canvasAnchorPane, infoLabel);
            this.currentTool.start();

            isMoved = true;
        });

        button.setOnMouseClicked(mouseEvent -> {

            if (isMoved) {
                isMoved = false;
                return;
            }

            if(inspectorNode.getIsActive() && inspectorNode.getNodeButtonControl() == this) {

                if(isChildShow){

                    hideChildNodes();
                    isChildShow = false;
                } else {

                    show();
                    isChildShow = true;
                }
            }

            inspectorNode.show();
            inspectorNode.setNode(this);
        });
    }

    /** Метод задает стиль кнопки.
     * Получает название css параметра и значение, которое нужно вставить */
    public void setStyle (String nameStyle, String valueString) {

        int startIndex = button.getStyle().indexOf(nameStyle);
        int endIndex = button.getStyle().indexOf(";") + 1;

        if(startIndex != -1) {

            String styleStart = button.getStyle().substring(0, startIndex);
            String styleEnd = button.getStyle().substring(endIndex + 1);

            button.setStyle(styleStart + nameStyle + ":" + valueString + ";" + styleEnd);

            return;
        }

        String style = button.getStyle();
        button.setStyle(style + nameStyle + ":" + valueString + ";");
    }

    /** Задает цвет ноду */
    public void setColorNode (Color color) {

        setStyle("-fx-background-color", "rgb(" + color.getRed() * 255 + ", " + color.getGreen() * 255 + ", " + color.getBlue() * 255 + ")");

        for (NodeButtonControl item: childButtonControls) {

            item.getLineToParent().setStroke(color);
        }

        node.uiNode.color = color;
    }

    /** Задает радиус нода */
    public void setRadius (double radius) {

        button.setPrefSize(radius, radius);
        button.setMaxSize(radius, radius);

        node.uiNode.radius = radius;
    }

    /** Возвращает названия нода */
    public String toString () {
        return node.name;
    }

    /** Задает наззвания нода */
    public void setName (String name) {
        node.name = name;
        button.setText(name);
    }

    /** Задает нода-родителя */
    public void setParent(NodeButtonControl parent) {
        this.parent = parent;
    }

    /** Возвращает нода-родителя */
    public NodeButtonControl getParent () {
        return parent;
    }

    /** Задает линию связывающую нод и его родителя */
    public void setLineToParent (Line lineToParent) {
        this.lineToParent = lineToParent;
    }

    /** Возвращает линию связывающую нод и его родителя */
    public Line getLineToParent () {return lineToParent;}

    /** Возвращает связанный с этим ui представлением нода, нод проекта */
    public Node getNode () {
        return node;
    }

    /** Возвращает возвращает кнопку нода на карте */
    public Button getButton () {
        return button;
    }

    /** Возвращает true если дочерние ноды отображаются на экране  */
    public boolean getIsChildShow () {return isChildShow;}

    /** Включает отображение дочерних нодов  */
    public void show () {
        changeVisibleParentNodes(this);
        changeVisibleChildNodes(this, true);
    }

    /** Скрывает дочерние ноды  */
    public void hideChildNodes () {
        changeVisibleChildNodes(this, false);
    }

    private  void  changeVisibleChildNodes (NodeButtonControl nodeButtonControl, boolean b) {

        for (NodeButtonControl item: nodeButtonControl.childButtonControls) {

            if((item.getIsChildShow() && b) || !b)
                changeVisibleChildNodes(item, b);

            item.getButton().setVisible(b);
            if(item.getLineToParent() != null)
                item.getLineToParent().setVisible(b);
        }
    }

    private  void  changeVisibleParentNodes (NodeButtonControl nodeButtonControl) {

        if(nodeButtonControl.getParent() != null) {
            changeVisibleParentNodes(nodeButtonControl.getParent());
        }

        nodeButtonControl.getButton().setVisible(true);
        if(nodeButtonControl.getLineToParent() != null)
            nodeButtonControl.getLineToParent().setVisible(true);
    }
}
