package Ex2;

import Ex2.api.DirectedWeightedGraph;
import Ex2.api.EdgeData;
import Ex2.api.NodeData;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

//TODO: test

public class MyGraph implements DirectedWeightedGraph {
    int nodesSize, edgesSize, MC;
    HashMap<String, EdgeData> edges;
    HashMap<Integer, NodeData> nodes;
    Iterator<EdgeData> edgeItr;
    Iterator<NodeData> nodeItr;
    ArrayList<EdgeData>[] ConnectedTo;

    public MyGraph(String json_file) {
        try {
            Gson gson = new Gson();
            Reader reader = Files.newBufferedReader(Paths.get(json_file));
            HashMap<?, ?> map = gson.fromJson(reader, HashMap.class);
            String E = map.get("Edges").toString();
            E = E.replace("{", "");
            E = E.substring(1, E.length() - 2);
            String[] Edges = E.split("}, ");
            String N = map.get("Nodes").toString();
            N = N.replace("{", "");
            N = N.substring(1, N.length() - 2);
            String[] Nodes = N.split("}, ");
            this.nodesSize = Nodes.length;
            this.edgesSize = Edges.length;
            //ArrayList<Integer> a = new ArrayList<>();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.edges = new HashMap<>();
        this.nodes = new HashMap<>();
        this.ConnectedTo = new ArrayList[nodesSize];
        for (int i = 0; i < this.nodesSize; i++) {
            this.nodes.put(i, new Node(json_file, i));
            this.ConnectedTo[i] = new ArrayList<>();
        }
        for (int i = 0; i < this.edgesSize; i++) {
            Edge e = new Edge(json_file, i);
            String key = Integer.toString(e.getSrc());
            key = key + ",";
            key = key + Integer.toString(e.getDest());
            this.edges.put(key, new Edge(json_file, i));
            int tmp = e.getSrc();
            this.ConnectedTo[e.getSrc()].add(e);
        }
        this.MC = 0;
        if (!nodes.isEmpty() && !edges.isEmpty()) {
            this.nodeItr = nodes.values().iterator();
            this.edgeItr = edges.values().iterator();
        }
    }

    public MyGraph(HashMap<String, EdgeData> edges, HashMap<Integer, NodeData> nodes) {
        this.edges = edges;
        this.nodes = nodes;
        this.MC = 0;
        this.edgesSize = edges.size();
        this.nodesSize = nodes.size();
        if (!nodes.isEmpty() && !edges.isEmpty()) {
            this.nodeItr = nodes.values().iterator();
            this.edgeItr = edges.values().iterator();
        }
    }
    public ArrayList<EdgeData>[] getConnectedTo() {
        return ConnectedTo;
    }

    public void setConnectedTo(ArrayList<EdgeData>[] connectedTo) {
        ConnectedTo = connectedTo;
    }

    public HashMap<String, EdgeData> getEdges() {
        return edges;
    }

    public HashMap<Integer, NodeData> getNodes() {
        return nodes;
    }

    @Override
    public NodeData getNode(int key) {
        try {
            return this.nodes.get(key);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public EdgeData getEdge(int src, int dest) {
        String key = Integer.toString(src) + "," + Integer.toString(dest);
        try {
            return this.edges.get(key);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void addNode(NodeData n) {
        this.nodesSize++;
        Node tmp = new Node();
        tmp.setTag(n.getTag());
        tmp.setLocation(n.getLocation());
        tmp.setWeight(n.getWeight());
        tmp.setInfo(n.getInfo());
        tmp.setId(nodesSize - 1);
        this.nodes.put(nodesSize - 1, tmp);
        MC++;
    }

    @Override
    public void connect(int src, int dest, double w) {
        this.edgesSize++;
        Edge tmpEdge = new Edge();
        tmpEdge.setDest(dest);
        tmpEdge.setSrc(src);
        tmpEdge.setWeight(w);
        String key = Integer.toString(src) + "," + Integer.toString(dest);
        this.edges.put(key, tmpEdge);
        MC++;
    }

    @Override
    public Iterator<NodeData> nodeIter() {
        if (MC > 0) {
            throw new RuntimeException("since the graph was change");
        } else {
            this.nodeItr = nodes.values().iterator();
            return this.nodeItr;
        }
    }

    @Override
    public Iterator<EdgeData> edgeIter() {
        if (MC > 0) {
            throw new RuntimeException("since the graph was change");
        } else {
            this.edgeItr = edges.values().iterator();
            return this.edgeItr;
        }
    }

    @Override
    public Iterator<EdgeData> edgeIter(int node_id) {
        ArrayList<EdgeData> tmp = new ArrayList<>();
        if (MC > 0) {
            throw new RuntimeException("since the graph was change");
        } else {
            for (EdgeData e : this.edges.values()) {
                if (e.getSrc() == node_id) {
                    tmp.add(e);
                }
            }
        }
        return tmp.iterator();
    }

    @Override
    public NodeData removeNode(int key) { //TODO: remove all edges from the hashmap
        this.nodesSize--;
        NodeData tmp = this.nodes.get(key);
        this.nodes.remove(key);
        MC++;
        int size = this.ConnectedTo[key].size();

        for (int i = 0; i < size; i++) {
            removeEdge(this.ConnectedTo[key].get(i).getSrc(), this.ConnectedTo[key].get(i).getDest());
        }
        this.ConnectedTo[key].clear();
        return tmp;
    }

    @Override
    public EdgeData removeEdge(int src, int dest) {
        String key = Integer.toString(src) + "," + Integer.toString(dest);
        EdgeData tmp = this.edges.get(key);
        this.edges.remove(key);
        this.edgesSize--;
        MC++;
        return tmp;
    }

    @Override
    public int nodeSize() {
        return nodesSize;
    }

    @Override
    public int edgeSize() {
        return edgesSize;
    }

    @Override
    public int getMC() {
        return this.MC;
    }
}