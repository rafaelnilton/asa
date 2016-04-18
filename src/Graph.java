import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Graph {
	
	private static Scanner scanner;
	private int _numVertices;
	private int _numEdges;
	private List<Edge> _edges = new ArrayList<Edge>();
	
	public Graph(int numVertices, int numEdges) {
		_numVertices = numVertices;
		_numEdges = numEdges;
	}
	
	public int getNumVertices() {
		return _numVertices;
	}
	
	public void setNumVertices(int numVertices) {
		_numVertices = numVertices;
	}
	
	public int getNumEdges() {
		return _numEdges;
	}
	
	public void setNumEdges(int numEdges) {
		_numEdges = numEdges;
	}
	
	public Edge getEdge(int index) {
		return _edges.get(index);
	}
	
	public void setEdge(List<Edge> edges) {
		_edges = edges;
	}
	
	public void addEdge(Edge edge) {
		_edges.add(edge);
	}
	
	public class Edge {
		private int _src;
		private int _dest;
		private int _weight;

		public Edge(int src, int dest, int weight) {
			_src = src;
			_dest = dest;
			_weight = weight;
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
	
	public int[] BellmanFord(int src, int[] totalDist)
	{
		int numVertices = getNumVertices();
		int numEdges = getNumVertices();
		int[] dist = new int[numVertices];
		
		for (int i = 0; i < numVertices; i++){
			dist[i] = Integer.MAX_VALUE;
		}
		dist[src] = 0;
		
		for (int i = 0; i < numVertices - 1; i++)
		{
			for (int j = 0; j < numEdges; j++)
			{
				Edge edge = getEdge(j);
				int u = edge.getSrc();
				int v = edge.getDest();
				int weight = edge.getWeight();
				if (dist[u] != Integer.MAX_VALUE && dist[u] + weight < dist[v]){
					dist[v] = dist[u] + weight;
					if(totalDist[v] != Integer.MAX_VALUE){
						totalDist[v] += dist[v];
					} else {
						totalDist[v] = dist[v];
					}
				}
			}
		}
		return dist;
		
	}

	public static void main(String[] args) {
		
		scanner = new Scanner(System.in);		
		String[] input = scanner.nextLine().split(" ");
		int n = Integer.parseInt(input[0]);
		int f = Integer.parseInt(input[1]);
		int c = Integer.parseInt(input[2]);
		int[][] allDists = new int[f][1];
		String[] subsidiaries = scanner.nextLine().split(" ");
		
		Graph graph = new Graph(n, c);
		int i=0;
		while(i < c) {
			String[] edge = scanner.nextLine().split(" ");
			int src = Integer.parseInt(edge[0]);
			int dest = Integer.parseInt(edge[1]);
			int weight = Integer.parseInt(edge[2]);
			Edge newEdge = graph.new Edge(src-1, dest-1, weight);
			graph.addEdge(newEdge);
			
			i++;
		}
		
		int[] totalDist = new int[n];
		for(int t : totalDist) {
			totalDist[t] = Integer.MAX_VALUE; 
		}
		
		int y = 0;
		for(String s : subsidiaries) {
			int subsidiary = Integer.parseInt(s);
			allDists[y] = graph.BellmanFord(subsidiary, totalDist);
			y++;
		}
		
		int value = 0;
		for(int z = 0; z < n ; z++) {
			if(totalDist[z] < value) {
				value = z;
			}
		}
		System.out.println("value: " + value+1);
		
		

	}

}
