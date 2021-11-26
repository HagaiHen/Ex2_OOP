package Ex2.tests;

import Ex2.Edge;
import Ex2.Landmark;
import Ex2.MyGraph;
import Ex2.Node;
import Ex2.api.NodeData;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


class MyGraphTest {
    MyGraph g = new MyGraph("src/Ex2/data/G3.json");
    Landmark pos = new Landmark(35.212217299435025, 32.106235628571426, 0.0);
    Node n = new Node(pos, "", 0);
    Edge e = new Edge(0, 1, 1.0286816758196655, "");

    @Test
    void getNode() {
        g.getNode(0);
        assertEquals(g.getNode(0).getLocation().x(), n.getLocation().x());
        assertEquals(g.getNode(0).getLocation().y(), n.getLocation().y());
        assertEquals(g.getNode(0).getLocation().z(), n.getLocation().z());
        assertEquals(g.getNode(0).getKey(), n.getKey());
    }

    @Test
    void getEdge() {

        assertEquals(e.getSrc(), g.getEdge(0, 1).getSrc());
        assertEquals(e.getDest(), g.getEdge(0, 1).getDest());
        assertEquals(e.getTag(), g.getEdge(0, 1).getTag());
        assertEquals(e.getInfo(), g.getEdge(0, 1).getInfo());
        assertEquals(e.getWeight(), g.getEdge(0, 1).getWeight());
    }

    @Test
    void addNode() {
        Landmark tmpPos = new Landmark(8, 4, 5);
        int tmpSize = g.nodeSize();
        boolean b = false;
        Node tmpNode = new Node(tmpPos, "", g.nodeSize());
        g.addNode(tmpNode);
        assertEquals(tmpSize + 1, g.nodeSize());
        if (g.getNode(tmpSize) != null)
            b = true;
        assertTrue(b);
    }

    @Test
    void connect() {
        boolean b = false;
        g.connect(0, 7, 1.2);
        if (g.getEdge(0, 7) != null) {
            b = true;
        }
        assertEquals(g.getEdge(0, 7).getWeight(), 1.2);
        assertTrue(b);
    }

    @Test
    void nodeIter() { //point to another place with the same values
        //HashMap<Integer, NodeData> tmp = g.getNodes();
        Iterator<NodeData> it = g.nodeIter();
        int i = 0;
        boolean b = true;
        it.next();
        while(it.hasNext()) {
            if (it != g.getNodes().get(i)) {
                b = false;
            }
            i++;
            it.next();
        }
        assertTrue(b);


    }

    @Test
    void edgeIter() {
    }

    @Test
    void testEdgeIter() {
    }

    @Test
    void removeNode() {
        boolean b = false;
        int size = g.getConnectedTo()[0].size();
        g.removeNode(0);
        if (g.getNode(0) == null) {
            b = true;
        }
        assertTrue(b);
        b = false;
        assertEquals(0, g.getConnectedTo()[0].size());
        if (g.getEdge(0, 1) == null && g.getEdge(0, 2) == null && g.getEdge(0, 8) == null && g.getEdge(0, 9) == null) {
            b = true;
        }
        assertTrue(b);
        b = false;
        assertEquals(1 + size, g.getMC());
        size = g.getConnectedTo()[1].size();
        g.removeNode(1);
        assertEquals(5 + 1 + size, g.getMC());
    }

    @Test
    void removeEdge() {
        int size = g.edgeSize();
        boolean b = false;
        g.removeEdge(0,1);
        assertEquals(size, g.edgeSize() + 1);
        if (g.getEdge(0,1) == null) {
            b = true;
        }
        assertTrue(b);
    }

    @Test
    void nodeSize() {
        assertTrue(g.nodeSize() == 48);
        g.removeNode(0);
        assertTrue(g.nodeSize() == 47);
    }

    @Test
    void edgeSize() {
        assertTrue(g.edgeSize() == 166);
        g.removeEdge(0,1);
        assertTrue(g.edgeSize() == 165);
    }

    @Test
    void getMC() {
        assertTrue(g.getMC() == 0);
        g.removeEdge(4,5);
        assertTrue(g.getMC() == 1);
        g.removeNode(0);
        assertTrue(g.getMC() == 6);
    }
}