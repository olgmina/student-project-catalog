package Tool;

import UI.NodeButtonControl;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

/** Метод выполняет перемещение нода мышью.
 * Наследуется от интерфейса ITool */
public class MovedNodeTool implements ITool{

    NodeButtonControl nodeButtonControl;
    AnchorPane anchorPane;
    Label infoLabel;

    public MovedNodeTool (NodeButtonControl nodeButtonControl, AnchorPane anchorPane, Label infoLabel) {
        this.nodeButtonControl = nodeButtonControl;
        this.anchorPane = anchorPane;
        this.infoLabel = infoLabel;
    }

    @Override
    public void start() {
        nodeButtonControl.getButton().setOnMouseDragged(mouseEvent -> {

            double x, y;

            x = (nodeButtonControl.getButton().getTranslateX() + mouseEvent.getX()) - (nodeButtonControl.getButton().getWidth() / 2);
            y = (nodeButtonControl.getButton().getTranslateY() + mouseEvent.getY()) - (nodeButtonControl.getButton().getHeight() / 2);

            if((nodeButtonControl.getButton().getTranslateX() + mouseEvent.getX()) - (nodeButtonControl.getButton().getWidth() / 2) < 0)
                x = 0;

            if((nodeButtonControl.getButton().getTranslateY() + mouseEvent.getY()) - (nodeButtonControl.getButton().getHeight() / 2) < 0)
                y = 0;

            if((nodeButtonControl.getButton().getTranslateX() + mouseEvent.getX()) + (nodeButtonControl.getButton().getWidth() / 2) > anchorPane.getPrefWidth())
                x = (anchorPane.getPrefWidth() - (nodeButtonControl.getButton().getWidth()));

            if((nodeButtonControl.getButton().getTranslateY() + mouseEvent.getY()) + (nodeButtonControl.getButton().getHeight() / 2) > anchorPane.getPrefHeight())
                y = (anchorPane.getPrefHeight() - (nodeButtonControl.getButton().getHeight()));;

            nodeButtonControl.getButton().setTranslateX(x);
            nodeButtonControl.getButton().setTranslateY(y);
            nodeButtonControl.getNode().uiNode.posX = nodeButtonControl.getButton().getTranslateX();
            nodeButtonControl.getNode().uiNode.posY = nodeButtonControl.getButton().getTranslateY();

            movedLineToParent(nodeButtonControl);
            for (NodeButtonControl item : nodeButtonControl.childButtonControls) {
                movedLineToParent(item);
            }

            infoLabel.setText("moved node (" + mouseEvent.getX() + ", " + mouseEvent.getY() );
        });
    }

    /** Перемещение соединительной линии */
    public void movedLineToParent (NodeButtonControl nodeButtonControl) {

        if(nodeButtonControl.getLineToParent() != null){
            nodeButtonControl.getLineToParent().setStartX(nodeButtonControl.getParent().getButton().getTranslateX() + (nodeButtonControl.getParent().getButton().getPrefWidth() / 2));
            nodeButtonControl.getLineToParent().setStartY(nodeButtonControl.getParent().getButton().getTranslateY() + (nodeButtonControl.getParent().getButton().getPrefHeight() / 2));
            nodeButtonControl.getLineToParent().setEndX(nodeButtonControl.getButton().getTranslateX() + (nodeButtonControl.getButton().getPrefWidth() / 2));
            nodeButtonControl.getLineToParent().setEndY(nodeButtonControl.getButton().getTranslateY() + (nodeButtonControl.getButton().getPrefHeight() / 2));
        }
    }

    @Override
    public void end() {
        nodeButtonControl.getButton().setOnDragDetected(null);
    }
}
