package Test;

import ProjectTimeLine.TimeLine.Node;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NodeTest {

    @Test
    void addChildNode() {

        Node node1 = new Node();
        Node node2 = new Node();
        node1.addChildNode(node2);
        assertTrue(node1.getChildNodes().get(0) == node2);
    }

    @Test
    void addChildNodeNull() {

        Node node1 = new Node();
        node1.addChildNode(null);
        assertTrue(node1.getChildNodes().size() == 0);
    }
}