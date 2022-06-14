package UI;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.util.Optional;

/** Класс пор удалению нодов проекта */
public class NodeButtonControlDelete {

    private AnchorPane canvasAnchorPane;
    private TimeLineTreeView timeLineTreeView;
    private Label infoText;

    public NodeButtonControlDelete (AnchorPane canvasAnchorPane, TimeLineTreeView timeLineTreeView, Label infoText) {
        this.canvasAnchorPane = canvasAnchorPane;
        this.timeLineTreeView = timeLineTreeView;
        this.infoText = infoText;
    }

    /** Метод удаляет переданный нод, а также все его дочерние ноды */
    public void delete (NodeButtonControl nodeButtonControl) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Предупреждение");
        alert.setHeaderText("Внимание!");
        alert.setContentText("Вы действительно ходите удалить нод?.");

        Optional<ButtonType> option = alert.showAndWait();

        if (option.get() == null) {
            return;
        } else if (option.get() == ButtonType.CANCEL) {
            return;
        } else if (option.get() == ButtonType.CLOSE) {
            return;
        }


        while (nodeButtonControl.childButtonControls.size() > 0) {
            delete(nodeButtonControl.childButtonControls.get(0));
        }

        nodeButtonControl.childButtonControls.clear();

        canvasAnchorPane.getChildren().remove(nodeButtonControl.getButton());

        nodeButtonControl.getNode().getChildNodes().clear();

        if(nodeButtonControl.getParent() != null){

            nodeButtonControl.getParent().getNode().getChildNodes().remove(nodeButtonControl.getNode());
            nodeButtonControl.getParent().childButtonControls.remove(nodeButtonControl);

            if(nodeButtonControl.getLineToParent() != null) {
                canvasAnchorPane.getChildren().remove(nodeButtonControl.getLineToParent());
            }
        }

        infoText.setText("Node delete");

        timeLineTreeView.treeViewUpdate();
    }
}
