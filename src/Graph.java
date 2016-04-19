// A Java program for Bellman-Ford's single source shortest path
// algorithm.
import java.util.*;
import java.lang.*;
import java.io.*;

// A class to represent a connected, directed and weighted graph
public class Graph
{
    // A class to represent a weighted edge in graph
    public class Edge {
        int src, dest, weight;
        Edge() {
            src = dest = weight = 0;
        }
    };
 
    int V, E;
    Edge edge[];
 
    // Creates a graph with V vertices and E edges
    Graph(int v, int e)
    {
        V = v;
        E = e;
        edge = new Edge[e];
        for (int i=0; i<e; ++i)
            edge[i] = new Edge();
    }
 
    // The main function that finds shortest distances from src
    // to all other vertices using Bellman-Ford algorithm.  The
    // function also detects negative weight cycle
    void BellmanFord(Graph graph,int src)
    {
        int V = graph.V, E = graph.E;
        int dist[] = new int[V];
 
        // Step 1: Initialize distances from src to all other
        // vertices as INFINITE
        for (int i=0; i<V; ++i)
            dist[i] = Integer.MAX_VALUE;
        dist[src] = 0;
 
        // Step 2: Relax all edges |V| - 1 times. A simple
        // shortest path from src to any other vertex can
        // have at-most |V| - 1 edges
        for (int i=1; i<V; ++i)
        {
            for (int j=0; j<E; ++j)
            {
                int u = graph.edge[j].src;
                int v = graph.edge[j].dest;
                int weight = graph.edge[j].weight;
                if (dist[u]!=Integer.MAX_VALUE && dist[u]+weight<dist[v])
                    dist[v]=dist[u]+weight;
            }
        }
 
       
        printArr(dist, V);
    }
 
    // A utility function used to print the solution
    void printArr(int dist[], int V)
    {
        System.out.println("Vertex   Distance from Source");
        for (int i=0; i<V; ++i)
            System.out.println(i+"\t\t"+dist[i]);
    }
 
    // Driver method to test above function
    public static void main(String[] args)
    {

        //* reads input
        Scanner scanner = new Scanner(System.in);       
        String[] input = scanner.nextLine().split(" ");
        String[] subsidiaries = scanner.nextLine().split(" ");

        //* creates the graph
        int V = Integer.parseInt(input[0]);
        int f = Integer.parseInt(input[1]);
        int E = Integer.parseInt(input[2]);

        Graph graph = new Graph(V, E);

        //* adds the edges
        for(int i = 0; i < E; i++) {
            String[] input2 = scanner.nextLine().split(" ");
            int u = Integer.parseInt(input2[0]);
            int v = Integer.parseInt(input2[1]);
            int w = Integer.parseInt(input2[2]);
            graph.edge[i].src = u-1;
            graph.edge[i].dest = v-1;
            graph.edge[i].weight = w;
        }

        //* calls BellmanFord for each subsidiary
        for(int i = 0; i < f; i++) {
            int subsidiary = Integer.parseInt(subsidiaries[i]);
            graph.BellmanFord(graph, subsidiary-1);
        }
    }
}