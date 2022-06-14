package UI;

import ProjectTimeLine.TimeLine.Node;
import Tool.ITool;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

/** Класс по созданию ui представления нода на карте */
public class NodeButtonControlBuilder {

    Node node;
    ITool currentTool;
    AnchorPane canvasAnchorPane;
    InspectorNode inspectorNode;
    Label infoLabel;

    public NodeButtonControlBuilder (Node node, ITool currentTool, AnchorPane canvasAnchorPane, InspectorNode inspectorNode, Label infoLabel) {

        this.node = node;
        this.currentTool = currentTool;
        this.canvasAnchorPane = canvasAnchorPane;
        this.inspectorNode = inspectorNode;
        this.infoLabel = infoLabel;
    }

    /** Метод возвращает созданный нод */
    public NodeButtonControl create () {

        NodeButtonBuilder nodeButtonBuilder = new NodeButtonBuilder(node);
        Button button = nodeButtonBuilder.create();
        NodeButtonControl nodeButtonControl = new NodeButtonControl(node, button, currentTool, canvasAnchorPane, inspectorNode, infoLabel);

        return  nodeButtonControl;
    }
}
