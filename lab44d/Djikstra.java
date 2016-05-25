package lab44d;

/**
 * Created by Alia Karssli
 */

import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Djikstra {
    int dist[];
    Boolean[] visited;
    int V,E;
    int[][] graph;

    Djikstra(int v, int e){
        this.V =v; // # of Vertices
        this.E = e; // # of Edges

        // Initialize distance array, visited array and vertex matrix
        dist = new int[V];
        visited = new Boolean[V];
        graph = new int[E][E];

        // Set all distances to max and visited to false
        for (int i = 0; i < V; i++)
        {
            dist[i] = Integer.MAX_VALUE;
            visited[i] = false;
        }
    }

    // Add edge to matrix, since this is undirected graph both vertex entries are changed
    void addEdge(int u, int v, int w)  {
        graph[u][v] = w;
        graph[v][u] = w;

    }

    // Get the vertex with the next smallest distance
    int minDistance(int dist[], Boolean sptSet[])
    {
        // Initialize min value
        int min = Integer.MAX_VALUE, min_index=-1;

        for (int v = 0; v < V; v++)
            if (!sptSet[v] && dist[v] <= min)
            {
                min = dist[v];
                min_index = v;
            }

        return min_index;
    }

    void setSources(int[] sources){

        for (int source : sources) {
            dist[source] = 0;
        }

        // Dijkstra's algorithm
        for (int count = 0; count < V-1; count++)
        {
            // Get the shortest distance vertex
            int u = minDistance(dist, visited);

            // Mark picked vertex
            visited[u] = true;

            // Change dist of adjacent vertexes
            for (int v = 0; v < V; v++)

                // Check if visited, edge exists, and distance is shorter
                if (!visited[v] && graph[u][v]!=0 && dist[u] != Integer.MAX_VALUE && dist[u]+graph[u][v] < dist[v]){
                    dist[v] = dist[u] + graph[u][v];
                }
        }
    }


    // Print vertex distances
    void printSolution( )
    {
        System.out.println("Vertex   Prev   Distance from Source");
        for (int i = 0; i < V; i++)
            System.out.println(i+" \t\t "+dist[i]);
    }

    // Driver method
    public static void main(String args[])
    {
        int V,E;

        try {
            Scanner file = new Scanner(new FileReader("C:\\Projects\\Alias HW\\linked lists\\src\\EWG.txt"));

            V = file.nextInt();
            E = file.nextInt();

            Djikstra djik = new Djikstra(V, E);

            // Add edges from file to Program
            for(int i = 0; i<E; i++){
                djik.addEdge(file.nextInt(), file.nextInt(), file.nextInt());
            }

            // Set source nodes
            int[] sources = {7,14,25,28,40};
            djik.setSources(sources);

            // Print vertex distances
            djik.printSolution();

        } catch (IOException e) {
            System.out.println("Loading File failed");
        }
    }
}