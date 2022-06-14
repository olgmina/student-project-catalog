package Tool;

import ProjectTimeLine.TimeLine.Node;
import UI.*;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

/** Класс создания и автоматического размещение нодов.
 * Наследуется от интерфейса ITool */
public class CreateNodeButtonControlTool implements ITool {

    private Node node;
    private ITool currentTool;
    private InspectorNode inspectorNode;
    private AnchorPane canvasAnchorPane;
    private TimeLineTreeView timeLineTreeView;
    private Label infoLabel;

    public CreateNodeButtonControlTool(Node node, ITool iTool, InspectorNode inspectorNode, AnchorPane canvasAnchorPane, TimeLineTreeView timeLineTreeView, Label infoLabel) {

        this.node = node;
        this.inspectorNode = inspectorNode;
        this.currentTool = iTool;
        this.canvasAnchorPane = canvasAnchorPane;
        this.timeLineTreeView = timeLineTreeView;
        this.infoLabel = infoLabel;
    }

    @Override
    public void start() {

        create(null, node);

        timeLineTreeView.treeViewUpdate();
    }

    @Override
    public void end() {

    }

    /** Метод создает и размещает переданный нод, а также все его дочерние ноды */
    public NodeButtonControl create(NodeButtonControl parent, Node node) {

        NodeButtonControlBuilder nodeButtonControlBuilder = new NodeButtonControlBuilder(node, currentTool, canvasAnchorPane, inspectorNode, infoLabel);
        NodeButtonControl nodeButtonControl = nodeButtonControlBuilder.create();

        PlaceNodeTool placeNodeTool = new PlaceNodeTool(parent, nodeButtonControl, canvasAnchorPane, timeLineTreeView, infoLabel);
        placeNodeTool.setIsNoAddInParentChildNodes(false);
        placeNodeTool.placeButtonNode(node.uiNode.posX, node.uiNode.posY);

        for (Node item: node.getChildNodes()) {
            create(nodeButtonControl, item);
        }

        return nodeButtonControl;
    }
}
