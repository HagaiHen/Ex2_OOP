package Ex2.tests;

import Ex2.Landmark;
import Ex2.Node;
import com.google.gson.Gson;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

public class main {
    public static void main(String[] args) {

/*
        try {
            String file_name = "src/Ex2/data/G3.json";
            // create Gson instance
            Gson gson = new Gson();

            // create a reader
            Reader reader = Files.newBufferedReader(Paths.get(file_name));

            // convert JSON file to map
            HashMap<?, ?> map = gson.fromJson(reader, HashMap.class);

            // print map entries
            for (Map.Entry<?, ?> entry : map.entrySet()) {
                System.out.println(entry.getKey() + "=" + entry.getValue());
            }
            String E = map.get("Edges").toString();
            String N = map.get("Nodes").toString();
            E=E.replace("{", "");
            E=E.substring(1,E.length()-2);
            String[] Edges = E.split("}, ");
            for (int i = 0; i < Edges.length; i++) {
                System.out.println(Edges[i]);
            }
            N=N.replace("{", "");
            N=N.substring(1,N.length()-2);
            String[] Nodes = N.split("}, ");
            for (int i = 0; i < Nodes.length; i++) {
                System.out.println(Nodes[i]);
            }

            // close reader
            reader.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
*/
        int index = 0;
        try {
            Gson gson = new Gson();
            Reader reader = Files.newBufferedReader(Paths.get("src/Ex2/data/G3.json"));
            HashMap<?, ?> map = gson.fromJson(reader, HashMap.class);
            String E = map.get("Edges").toString();
            E = E.replace("{", "");
            E = E.substring(1, E.length() - 2);
            String[] Edges = E.split("}, ");
            String[] tmp = Edges[index].split(",");
            tmp[0] = tmp[0].replace("src=","");
            System.out.println(tmp[0]);
            tmp[1] = tmp[1].replace(" w=","");
            System.out.println(tmp[1]);
            tmp[2] = tmp[2].replace(" dest=","");
            System.out.println(tmp[2]);
            reader.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        Landmark l = new Landmark("src/Ex2/data/G3.json", 0);
        System.out.println(l);
        Node n = new Node("src/Ex2/data/G3.json", 0);
        System.out.println(n);

        Node tmp = new Node();
        tmp.setTag(n.getTag());
        tmp.setLocation(n.getLocation());
        tmp.setWeight(n.getWeight());
        tmp.setInfo(n.getInfo());
        System.out.println("tmp is: " + tmp);

    }
}