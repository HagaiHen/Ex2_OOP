package Ex2.tests;

import Ex2.MyGraph;
import Ex2.MyGraphAlgo;
import Ex2.api.DirectedWeightedGraph;
import Ex2.api.EdgeData;
import Ex2.api.NodeData;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MyGraphAlgoTest {
    MyGraph g = new MyGraph("src/Ex2/data/G3.json");
    MyGraphAlgo g2 = new MyGraphAlgo(g);

    @Test
    void init() {
    }

    @Test
    void getGraph() {
    }

    @Test
    void copy() {
        DirectedWeightedGraph tmp = g2.copy();
        System.out.println(tmp.toString() + ", " + g.toString());
        assertNotEquals(tmp.toString(), g.toString());
        assertEquals(tmp.getEdge(0, 1).getWeight(), g.getEdge(0, 1).getWeight());
        assertEquals(tmp.nodeSize(), g.nodeSize());
        assertEquals(tmp.edgeSize(), g.edgeSize());
        Iterator<NodeData> itG = g.nodeIter();
        Iterator<NodeData> itTmp = tmp.nodeIter();
        boolean b = true;
        for (int i = 0; i < g.nodeSize(); i++) {
            while (itG.hasNext()) {
                NodeData n1 = itG.next();
                NodeData n2 = itTmp.next();
                if (n1.getLocation().x() != n2.getLocation().x()
                        || n1.getLocation().y() != n2.getLocation().y()
                        || n1.getLocation().z() != n2.getLocation().z()
                        || n1.getKey() != n2.getKey()) {
                    b = false;
                }
            }
            assertTrue(b);
        }
        Iterator<EdgeData> iterG = g.edgeIter();
        Iterator<EdgeData> iterTmp = tmp.edgeIter();
        b = true;
        while (iterG.hasNext()) {
            EdgeData e1 = iterG.next();
            EdgeData e2 = iterTmp.next();
            if (e1.getSrc() != e2.getSrc() || e1.getDest() != e2.getDest() || e1.getWeight() != e2.getWeight()) {
                b = false;
            }
        }
        assertTrue(b);
    }

    @Test
    void isConnected() { //To complete
        MyGraph tmp = new MyGraph("src/Ex2/data/G1.json");
        MyGraphAlgo Conntected = new MyGraphAlgo(tmp);
        assertTrue(Conntected.isConnected());
        tmp = new MyGraph("src/Ex2/data/G1_copy.json");
        MyGraphAlgo NotConnected = new MyGraphAlgo(tmp);
        assertFalse(NotConnected.isConnected());
    }

    @Test
    void shortestPathDist() {
        MyGraph g = new MyGraph("src/Ex2/data/G3.json");
        MyGraphAlgo graph = new MyGraphAlgo(g);
        assertEquals(1.7420530403455134, this.g2.shortestPathDist(15,40));
    }

    @Test
    void shortestPath() {
    }

    @Test
    void center() {
    }

    @Test
    void tsp() {
        MyGraph g = new MyGraph("src/Ex2/data/G3.json");
        MyGraphAlgo graph = new MyGraphAlgo(g);
        List<NodeData> n = new ArrayList<>();
        n.add(this.g.getNode(0));
        n.add(this.g.getNode(1));
        n.add(this.g.getNode(2));
        n.add(this.g.getNode(3));
        n.add(this.g.getNode(9));
        graph.tsp(n);
    }

    @Test
    void save() {
    }

    @Test
    void load() {
        MyGraphAlgo graph = new MyGraphAlgo(g);
        graph.load("src/Ex2/data/G1.json");

    }
}