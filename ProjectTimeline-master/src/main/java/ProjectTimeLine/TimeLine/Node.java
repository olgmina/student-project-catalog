package ProjectTimeLine.TimeLine;

import java.util.ArrayList;
import java.util.List;

/** Содержит информацию о ноде */
public class Node {

    public String name;
    public String description;

    /** Содержит информацию о графическом представлении нода */
    public UINode uiNode;

    /** Хранит ссылки на дочерние ноды */
    private List<Node> childNodes;

    /** Конструктор инициализации нода
     * Инициализирует массив дочерних элементов*/
    public Node () {
        name = "none";
        description = "";
        childNodes = new ArrayList<Node>();
    }

    public List<Node> getChildNodes () {
        return childNodes;
    }

    public void addChildNode (Node node) {

        if(node == null)
            return;

        childNodes.add(node);
    }
}
