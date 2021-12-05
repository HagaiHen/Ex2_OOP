package Ex2.tests;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.util.Random;

public class GenerateGraph {
    GenerateEdge[] Edges;
    GenerateNode[] Nodes;

    public GenerateGraph(int EdgeSize, int NodeSize) {
        this.Edges = new GenerateEdge[EdgeSize];
        this.Nodes = new GenerateNode[NodeSize];
        int i = 0, j = 0;
        String pos = "";
        Random r = new Random();
        while (i < NodeSize) {
            pos = (35 + r.nextDouble()) + "," + (32 + r.nextDouble()) + "," + "0.0";
            this.Nodes[i] = new GenerateNode(pos, i);
            i++;
        }
        while (j < EdgeSize) {
            this.Edges[j] = new GenerateEdge(r.nextInt(NodeSize), r.nextDouble(), r.nextInt(NodeSize));
            j++;
        }
    }

    public void GenerateJson(GenerateGraph g) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try {
            FileWriter w = new FileWriter("src/Ex2/data/new.json");
            gson.toJson(g, w);
            w.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
