package lab43c;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Kruskal {
    private int V;   // No. of edges
    private int E;   // No. of edges
    private ArrayList<Edge> edges; //Edge List
    private PriorityQueue<Edge> mst; // Minimum Spanning Tree
    private int[] parents;


    //Constructor
    Kruskal(int v, int e)
    {
        mst = new PriorityQueue<Edge>();
        edges = new ArrayList<Edge>();
        V = v;
        E = e;
        parents = new int[V];

    }

    // Function for creating Minimum Spanning Tree
    private void mst(){
        Collections.sort(edges,  new Comparator<Edge>() {
            @Override
            public int compare(Edge e1, Edge e2) {
                return e1.compareTo(e2);
            }
        }); // Sort edges
        for(int i=0; i<V;i++) parents[i] = -1; // Create subsets

        for(int i=0; i<E;i++){
            Edge edge = edges.get(i);
            int v = edge.v;
            int w = edge.w;

            // Find cycles using Union-find
            if(find(v) != find(w)){
                Union(v,w);
                mst.add(edge);
            }
        }
    }

    int find(int i)
    {
        if (parents[i] == -1)
            return i;
        return find(parents[i]);
    }

    // A utility function to do union of two subsets
    void Union(int x, int y)
    {
        int xset = find(x);
        int yset = find(y);
        parents[yset] = xset;
    }
    //Function to add an edge
    void addEdge(Edge e)  {
        edges.add(e);
    }


    // Driver method
    public static void main(String args[])
    {
        int V = 0;
        int E = 0;

        try {
            Scanner file = new Scanner(new FileReader("C:\\Projects\\Alias HW\\linked lists\\src\\EWG.txt"));

            V = file.nextInt();
            E = file.nextInt();

            Kruskal k = new Kruskal(V, E);

            // Add edges from file to Object
            for(int i = 0; i<E; i++){
                Kruskal.Edge e = k.new Edge(file.nextInt(),file.nextInt(), file.nextDouble());
                k.addEdge(e);
            }

            // Execute Kruskals on graph
            k.mst();

            int size = (k.mst.size());
            int total = 0;


            // Print Minimum spanning tree
            for(int i = 0; i<size;i++){
                Edge edge = k.mst.remove();
                total += edge.weight;
                System.out.println(edge.v + " " + edge.w);
            }

            System.out.println("Total length of cable: " + total);

        } catch (IOException e) {
            System.out.println("Loading File failed");
        }

    }


    // Edge class
    class Edge implements Comparable<Edge> {
        public final int v;
        public final int w;
        public final double weight;

        Edge(int v,int w,double  weight){
            this.v = v;
            this.w = w;
            this.weight = weight;
        }

        @Override
        public int compareTo(Edge that) {
            if      (this.weight < that.weight) return -1;
            else if (this.weight > that.weight) return +1;
            else                                    return  0;
        }
        @Override
        public boolean equals(Object other){
            if (other == null) return false;
            if (other == this) return true;
            if (!(other instanceof Edge))return false;

            Edge e = (Edge)other;
            if (this.v == e.w || this.v == e.v) return true;
            return false;
        }
    }
}