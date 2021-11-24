package Ex2;

import Ex2.api.DirectedWeightedGraph;
import Ex2.api.EdgeData;
import Ex2.api.NodeData;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;

class Graph implements DirectedWeightedGraph {
    int nodesSize, edgesSize;
    HashMap<String, Edge> edges;
    HashMap<Integer, Node> nodes;

    // Creates a graph with V vertices and E edges
    Graph(String json_file) {
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
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < this.edgesSize; i++) {
            Edge e = new Edge(json_file, i);
            String key = Integer.toString(e.getSrc());
            key = key + ",";
            key = key + Integer.toString(e.getDest());
            this.edges.put(key, new Edge(json_file, i));
        }
        for (int i = 0; i < this.nodesSize; i++) {
            this.nodes.put(i, new Node(json_file, i));
        }
    }

    // A utility function to find the subset of an element i
    int find(int parent[], int i) {
        if (parent[i] == -1)
            return i;
        return find(parent, parent[i]);
    }

    // A utility function to do union of two subsets
    void Union(int parent[], int x, int y) {
        parent[x] = y;
    }


    // The main function to check whether a given graph
    // contains cycle or not
    /*
    int isCycle(Graph graph) {
        // Allocate memory for creating V subsets
        int parent[] = new int[graph.V];

        // Initialize all subsets as single element sets
        for (int i = 0; i < graph.V; ++i)
            parent[i] = -1;

        // Iterate through all edges of graph, find subset of both
        // vertices of every edge, if both subsets are same, then
        // there is cycle in graph.
        for (int i = 0; i < graph.E; ++i) {
            int x = graph.find(parent, graph.edge[i].src);
            int y = graph.find(parent, graph.edge[i].dest);

            if (x == y)
                return 1;

            graph.Union(parent, x, y);
        }
        return 0;
    }

    // Driver Method
    public static void main(String[] args) {
        /* Let us create the following graph
        0
        | \
        |  \
        1---2
        int V = 3, E = 3;
        Graph graph = new Graph(V, E);

        // add edge 0-1
        graph.edge[0].src = 0;
        graph.edge[0].dest = 1;

        // add edge 1-2
        graph.edge[1].src = 1;
        graph.edge[1].dest = 2;

        // add edge 0-2
        graph.edge[2].src = 0;
        graph.edge[2].dest = 2;

        if (graph.isCycle(graph) == 1)
            System.out.println("graph contains cycle");
        else
            System.out.println("graph doesn't contain cycle");
    }
    */
    @Override
    public NodeData getNode(int key) {
        return this.nodes.get(key);
    }

    @Override
    public EdgeData getEdge(int src, int dest) {
        for (int i = 0; i < this.edgesSize; i++) {
            if (this.edges.get(i).getSrc() == src && this.edges.get(i).getDest() == dest) {
                return this.edges.get(i);
            }
        }
        return null;
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
    }

    @Override
    public void connect(int src, int dest, double w) {
        Edge tmpEdge = new Edge();
        tmpEdge.setDest(dest);
        tmpEdge.setSrc(src);
        tmpEdge.setWeight(w);
        String key = Integer.toString(src) + "," + Integer.toString(dest);
        this.edges.put(key, tmpEdge);
    }

    @Override
    public Iterator<NodeData> nodeIter() {
        return null;
    }

    @Override
    public Iterator<EdgeData> edgeIter() {
        return null;
    }

    @Override
    public Iterator<EdgeData> edgeIter(int node_id) {
        return null;
    }

    @Override
    public NodeData removeNode(int key) {
        this.nodesSize--;
        return this.nodes.remove(key);
    }

    @Override
    public EdgeData removeEdge(int src, int dest) {
        String key = Integer.toString(src) + "," + Integer.toString(dest);
        Edge tmp = this.edges.get(key);
        this.edges.remove(key);
        this.edgesSize--;
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
        return 0;
    }
}
