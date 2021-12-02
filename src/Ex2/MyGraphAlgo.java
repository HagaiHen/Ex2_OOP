package Ex2;

import Ex2.api.DirectedWeightedGraph;
import Ex2.api.DirectedWeightedGraphAlgorithms;
import Ex2.api.EdgeData;
import Ex2.api.NodeData;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.util.*;

public class MyGraphAlgo implements DirectedWeightedGraphAlgorithms {

    private DirectedWeightedGraph g;

    public MyGraphAlgo(DirectedWeightedGraph g) {
        this.g = g;
    }

    @Override
    public void init(DirectedWeightedGraph g) {
        this.g = g;
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

    private void DFS(DirectedWeightedGraph g, int v) {

        g.getNode(v).setTag(1); //mark as visited
        EdgeData e = new Edge();
        int n;
        Iterator<EdgeData> it = g.edgeIter(v);
        while (it.hasNext()) {
            e = it.next();
            n = e.getDest();
            if (g.getNode(n).getTag() == 0)
                DFS(g, n);
        }
    }

    private MyGraph getTranspose() {
        HashMap<String, EdgeData> edges = new HashMap<>();
        HashMap<Integer, NodeData> nodes = new HashMap<>();
        MyGraph tmp = new MyGraph(edges, nodes);
        Iterator<EdgeData> itE = this.g.edgeIter();
        EdgeData e = new Edge();
        e = itE.next();
        Iterator<NodeData> itN = this.g.nodeIter();
        NodeData n = new Node();

        while (itN.hasNext()) {
            n = itN.next();
            tmp.addNode(n);
            n.setTag(0);
        }
        while (itE.hasNext()) {
            e = itE.next();
            int dest = e.getSrc();
            int src = e.getDest();
            tmp.connect(src, dest, e.getWeight());
        }
        tmp.setMC(0);
        return tmp;
    }

    @Override
    public boolean isConnected() {
        DirectedWeightedGraph tmp = this.copy();
        Iterator<NodeData> it = tmp.nodeIter();
        NodeData n = new Node();
        DFS(tmp, 0);
        while (it.hasNext()) {
            n = it.next();
            if (n.getTag() == 0) {
                return false;
            }
        }

        tmp = this.getTranspose();
        it = tmp.nodeIter();
        while (it.hasNext()) {
            n = it.next();
            n.setTag(0);
        }
        DFS(tmp, 0);
        it = tmp.nodeIter();

        while (it.hasNext()) {
            n = it.next();
            if (n.getTag() == 0) {
                return false;
            }
        }
        return true;
    }

    @Override
    public double shortestPathDist(int src, int dest) {
        List<Integer> prev = new ArrayList<>();
        List<Double> dist = new ArrayList<>();
        PriorityQueue<Integer> q = new PriorityQueue<>();

        Iterator<NodeData> it = this.g.nodeIter();
        NodeData n = new Node();

        while (it.hasNext()) {
            n = it.next();
            if (n.getKey() != src)
                dist.add(n.getKey(), Double.MAX_VALUE);
            else {
                dist.add(n.getKey(), null);
            }
            prev.add(n.getKey(), null);
            q.add(n.getKey());
        }
        dist.remove(src);
        dist.add(src, 0.0);
        int u = src;
        while (!q.isEmpty()) {
            //int tmp = u;
            u = dist(dist, q, u);
            //dist.remove(u);
            //dist.add(u, this.g.getEdge(tmp, u).getWeight());
            q.remove(u);

            Iterator<Integer> iter = q.iterator();
            double alt = 0;
            int node = 0;
            while (iter.hasNext()) {
                node = iter.next();
                if (this.g.getEdge(u, node) != null && u != src && node != 0) {
                    alt = dist.get(u) + this.g.getEdge(u, node).getWeight();
                    if (alt < dist.get(node)) {
                        dist.remove(node);
                        dist.add(node, alt);
                        prev.remove(node);
                        prev.add(node, u);
                    }
                }
            }
        }
        return dist.get(dest);
    }

    private int dist(List<Double> dist, PriorityQueue<Integer> q, int src) {
        double min = Double.MAX_VALUE;
        int min_index = -1;
        Iterator<NodeData> it = this.g.nodeIter();
        NodeData n = new Node();

        while (it.hasNext()) {
            n = it.next();
            double tmp = 0;
            if (this.g.getEdge(src, n.getKey()) != null) {
                min_index = q.peek();
                tmp = this.g.getEdge(src, n.getKey()).getWeight() + dist.get(src);
                if (tmp < dist.get(n.getKey())) {
                    dist.remove(n.getKey());
                    dist.add(n.getKey(), tmp);
                }
                if (this.g.getEdge(src, n.getKey()).getWeight() <= min && q.contains(n.getKey()) && dist.get(n.getKey()) != 0) {
                    min = this.g.getEdge(src, n.getKey()).getWeight();
                    min_index = n.getKey();
                }
            }
        }
        return min_index;
    }

    private List<Integer> shortestPathHelper (int src, int dest) {
        List<Integer> prev = new ArrayList<>();
        List<Double> dist = new ArrayList<>();
        PriorityQueue<Integer> q = new PriorityQueue<>();

        Iterator<NodeData> it = this.g.nodeIter();
        NodeData n = new Node();

        while (it.hasNext()) {
            n = it.next();
            if (n.getKey() != src)
                dist.add(n.getKey(), Double.MAX_VALUE);
            else {
                dist.add(n.getKey(), null);
            }
            prev.add(n.getKey(), null);
            q.add(n.getKey());
        }
        dist.remove(src);
        dist.add(src, 0.0);
        int u = src;
        while (!q.isEmpty()) {
            int tmp = u;
            u = dist(dist, q, u);
            //dist.remove(u);
            //dist.add(u, this.g.getEdge(tmp, u).getWeight());
            q.remove(u);
            prev.remove(u);
            prev.add(u, tmp);
            Iterator<Integer> iter = q.iterator();
            double alt = 0;
            int node = 0;
            while (iter.hasNext()) {
                node = iter.next();
                if (this.g.getEdge(u, node) != null && u != src && node != 0) {
                    alt = dist.get(u) + this.g.getEdge(u, node).getWeight();
                    if (alt < dist.get(node)) {
                        dist.remove(node);
                        dist.add(node, alt);
                        prev.remove(node);
                        prev.add(node, u);
                    }
                }
            }
        }
        return prev;
    }

    @Override
    public List<NodeData> shortestPath(int src, int dest) {
        List<Integer> path = shortestPathHelper(src, dest);
        List<NodeData> ans = new ArrayList<>();
        int tmp = dest;
        while (tmp != src || this.g.getEdge(src, tmp) != null) {
            ans.add(this.g.getNode(tmp));
            tmp = path.get(tmp);
        }
        ans.add(this.g.getNode(tmp));
        return ans;
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
