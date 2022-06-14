package UI;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

/** Класс по обработке и отображению ui treeView   */
public class TimeLineTreeView {

    private TreeView<NodeButtonControl> timeLineTreeView;
    private InspectorNode inspectorNode;
    private NodeButtonControl startNode;

    public TimeLineTreeView (TreeView<NodeButtonControl> timeLineTreeView, InspectorNode inspectorNode) {
        this.timeLineTreeView = timeLineTreeView;
        this.inspectorNode = inspectorNode;
    }

    /** Задается начальный (корневой) нод  */
    public void setStartNode (NodeButtonControl startNode) {
        this.startNode = startNode;
    }

    /** Обновление (прерисовка) списка нодов */
    public void treeViewUpdate () {

        if(startNode == null)
            return;

        timeLineTreeView.setRoot(getTreViewItems(startNode));

        timeLineTreeView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TreeItem<NodeButtonControl>>() {

            @Override
            public void changed(ObservableValue<? extends TreeItem<NodeButtonControl>> observableValue, TreeItem<NodeButtonControl> nodeButtonControlTreeItem, TreeItem<NodeButtonControl> t1) {

                if(t1 == null)
                    return;

                NodeButtonControl nodeButtonControl = t1.getValue();

                nodeButtonControl.show();

                inspectorNode.setNode(nodeButtonControl);
                inspectorNode.show();
            }
        });
    }

    private TreeItem<NodeButtonControl> getTreViewItems (NodeButtonControl nodeButtonControl) {

        TreeItem<NodeButtonControl> nodeButtonControlTreeItem = new TreeItem<NodeButtonControl>(nodeButtonControl);

        for (NodeButtonControl item: nodeButtonControl.childButtonControls) {
            nodeButtonControlTreeItem.getChildren().add(getTreViewItems(item));
        }

        return nodeButtonControlTreeItem;
    }

}
