/**************************************************** 
**** [LEIC-A] Analysis and Synthesis of Algorithms
**** 2nd Project
**** 04/2016
*****************************************************
**** Rafael Martins, 82034
**** Rafael Nilton, 426866 
*****************************************************/

import java.util.Scanner;

public class Graph
{
    public class Edge {
        
        private int _src;
        private int _dest;
        private int _weight;

        public Edge() {
            _src = 0;
            _dest = 0;
            _weight = 0;
        }

        public int getSrc() {
            return _src;
        }

        public void setSrc(int src) {
            _src = src;
        }

        public int getDest() {
            return _dest;
        }

        public void setDest(int dest) {
            _dest = dest;
        }

        public int getWeight() {
            return _weight;
        }

        public void setWeight(int weight) {
            _weight = weight;
        }
    }
 
    private int _vertices;
    private int _edges;
    private static Edge _edge[];
 
    public Graph(int v, int e)
    {
        _vertices = v;
        _edges = e;
        _edge = new Edge[e];
        
        for (int i=0; i<e; ++i){
            _edge[i] = new Edge();
        }
    }
 
    public int getVertices() {
        return _vertices;
    }

    public void setVertices(int vertices) {
        _vertices = vertices;
    }

    public int getEdges() {
        return _edges;
    }

    public void setEdges(int edges) {
        _edges = edges;
    }

    public static Edge getEdge(int index) {
        return _edge[index];
    }

    public void setEdge(Edge[] edge) {
        _edge = edge;
    }
    
    public int[] bellmanFord(int src, int[] totalDist)
    {
        int vertices = getVertices();
        int edges = getEdges();
        int dist[] = new int[vertices];
 
        for (int i=0; i<vertices; ++i) {
            dist[i] = Integer.MAX_VALUE;
        }
        dist[src] = 0;
 
        for (int i=1; i<vertices; ++i)
        {
            for (int j=0; j<edges; ++j)
            {
                Edge edge = getEdge(j);
                int u = edge.getSrc();
                int v = edge.getDest();
                int weight = edge.getWeight();
                if (dist[u] != Integer.MAX_VALUE && dist[u] + weight < dist[v]){
                    dist[v] = dist[u] + weight;
                }
            }
        }
 
         return dist;
    }

    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);       
        String[] input = scanner.nextLine().split(" ");
        String[] subsidiaries = scanner.nextLine().split(" ");

        int V = Integer.parseInt(input[0]);
        int f = Integer.parseInt(input[1]);
        int E = Integer.parseInt(input[2]);

        Graph graph = new Graph(V, E);

        for(int i = 0; i < E; i++) {
            String[] input2 = scanner.nextLine().split(" ");
            int u = Integer.parseInt(input2[0]);
            int v = Integer.parseInt(input2[1]);
            int w = Integer.parseInt(input2[2]);
            Edge edge = getEdge(i);
            edge.setSrc(u-1);
            edge.setDest(v-1);
            edge.setWeight(w);
        }
        
        int[][] allDist = new int[f][1];
        int[] totalDist = new int[V];
        for(int i = 0; i < V; i++) {
            totalDist[i] = Integer.MAX_VALUE; 
        }

        for(int i = 0; i < f; i++) {
            int subsidiary = Integer.parseInt(subsidiaries[i]);
            allDist[i] = graph.bellmanFord(subsidiary-1, totalDist);
        }

        
        for (int i = 0; i < V; i++) {
            for(int y = 0; y < f; y++) {
                if (allDist[y][i] == Integer.MAX_VALUE) {
                    totalDist[i] = Integer.MAX_VALUE;
                    break;
                }
                else if(totalDist[i] != Integer.MAX_VALUE) 
                    totalDist[i] += allDist[y][i];
                else 
                    totalDist[i] = allDist[y][i];
            }
        } 

        int meetPoint = 0;
        for(int i = 0; i < V ; i++) {
            if(totalDist[i] < totalDist[meetPoint]) meetPoint = i;
        }
        int meetPointDist = totalDist[meetPoint];
        if (meetPointDist != Integer.MAX_VALUE) {
            System.out.println((meetPoint + 1) + " " + meetPointDist); // meeting point and respective total loss

            for (int i = 0; i < f; i++) {
                System.out.print(allDist[i][meetPoint] + " ");
            }
            System.out.println();
        }
        else System.out.println("N");
    }
}
