package Ex2;

import Ex2.api.DirectedWeightedGraph;
import Ex2.api.DirectedWeightedGraphAlgorithms;
import Ex2.api.EdgeData;
import Ex2.api.NodeData;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class MyGraphAlgo implements DirectedWeightedGraphAlgorithms {

    private DirectedWeightedGraph g;

    public MyGraphAlgo(DirectedWeightedGraph g) {
        this.g = g;
    }

    @Override
    public void init(DirectedWeightedGraph g) {

    }

    @Override
    public DirectedWeightedGraph getGraph() {
        return this.g;
    }

    @Override
    public DirectedWeightedGraph copy() {
        HashMap<Integer, NodeData> node = new HashMap<>();
        Iterator<NodeData> it = g.nodeIter();
        int i = 0;
        while (it.hasNext()) {
            NodeData n = it.next();
            node.put(i, n);
            i++;
        }


        HashMap<String, EdgeData> edge = new HashMap<>();
        Iterator<EdgeData> iter = g.edgeIter();
        while (iter.hasNext()) {
            EdgeData e = iter.next();
            String key = e.getSrc() + "," + e.getDest();
            edge.put(key, e);
        }
        DirectedWeightedGraph tmp = new MyGraph(edge, node);
        return tmp;
    }

    @Override
    public boolean isConnected() {
        return false;
    }

    @Override
    public double shortestPathDist(int src, int dest) {
        return 0;
    }

    @Override
    public List<NodeData> shortestPath(int src, int dest) {
        return null;
    }

    @Override
    public NodeData center() {
        return null;
    }

    @Override
    public List<NodeData> tsp(List<NodeData> cities) {
        return null;
    }

    @Override
    public boolean save(String file) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try {
            gson.toJson(g, new FileWriter("src/Ex2/data/file.json"));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean load(String file) {
        try {
            this.g = new MyGraph(file);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
