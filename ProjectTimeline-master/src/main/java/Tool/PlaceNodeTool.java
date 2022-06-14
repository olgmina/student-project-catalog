package Tool;

import UI.NodeButtonControl;
import UI.NodeLineConnectionBuilder;
import UI.TimeLineTreeView;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;

/** Класс размещения созданного нода по клику мыши.
 * Наследуется от интерфейса ITool */
public class PlaceNodeTool implements ITool{

    private NodeButtonControl parent;
    private NodeButtonControl nodeButtonControl;
    private AnchorPane canvasAnchorPane;
    private TimeLineTreeView timeLineTreeView;
    private Label infoLabel;

    private boolean isAddInParentChildNodes = true;

    public PlaceNodeTool(NodeButtonControl parent, NodeButtonControl nodeButtonControl, AnchorPane canvasAnchorPane, TimeLineTreeView timeLineTreeView, Label infoLabel) {

        this.parent = parent;
        this.nodeButtonControl = nodeButtonControl;
        this.canvasAnchorPane = canvasAnchorPane;
        this.timeLineTreeView = timeLineTreeView;
        this.infoLabel = infoLabel;
    }

    @Override
    public void start() {
        this.canvasAnchorPane.setOnMouseClicked(mouseEvent -> {
            placeButtonNode(mouseEvent.getX(), mouseEvent.getY());
        });
    }

    @Override
    public void end() {
        this.canvasAnchorPane.setOnMouseClicked(null);
    }

    public void setIsNoAddInParentChildNodes (boolean isAddInParentChildNodes) {
        this.isAddInParentChildNodes = isAddInParentChildNodes;
    }

    /** Метод размещения нода в переданных координатах */
    public void placeButtonNode(double posX, double posY) {

        canvasAnchorPane.getChildren().add(nodeButtonControl.getButton());

        nodeButtonControl.getButton().setTranslateX(posX);
        nodeButtonControl.getButton().setTranslateY(posY);
        nodeButtonControl.getNode().uiNode.posX = nodeButtonControl.getButton().getTranslateX();
        nodeButtonControl.getNode().uiNode.posY = nodeButtonControl.getButton().getTranslateY();

        if(parent != null) {

            NodeLineConnectionBuilder nodeLineConnectionBuilder = new NodeLineConnectionBuilder(canvasAnchorPane);
            Line line = nodeLineConnectionBuilder.create();

            line.setStroke(parent.getNode().uiNode.color);

            line.setStartX(parent.getButton().getTranslateX() + (parent.getButton().getPrefWidth() / 2));
            line.setStartY(parent.getButton().getTranslateY() + (parent.getButton().getPrefHeight() / 2));

            line.setEndX(nodeButtonControl.getButton().getTranslateX() + (nodeButtonControl.getButton().getPrefWidth() / 2));
            line.setEndY(nodeButtonControl.getButton().getTranslateY() + (nodeButtonControl.getButton().getPrefHeight() / 2));

            nodeButtonControl.setLineToParent(line);

            parent.childButtonControls.add(nodeButtonControl);

            if(isAddInParentChildNodes)
                parent.getNode().addChildNode(nodeButtonControl.getNode());

            nodeButtonControl.setParent(parent);
        }

        timeLineTreeView.treeViewUpdate();

        infoLabel.setText("Place Node");
        end();
    }
}
