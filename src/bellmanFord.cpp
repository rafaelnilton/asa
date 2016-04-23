/**************************************************** 
**** [LEIC-A] Analysis and Synthesis of Algorithms
**** 2nd Project
**** 04/2016
*****************************************************
**** Rafael Martins, 82034
**** Rafael Nilton, 426866 
*****************************************************/

#include <iostream> 
#include <stdlib.h>
#include <limits.h>
#include <vector>

using namespace std;
 
struct Edge {
    int src;
    int dest;
    int weight;
};
 
struct Graph {
    int V;
    int E; 
    struct Edge* edges;
};
 
vector<int> bellmanFord(struct Graph* graph, int src) {
    int V = graph->V;
    int E = graph->E;
    vector<int> dist(V);

    for (int i = 0; i < V; i++) 
        dist[i] = INT_MAX;
    
    dist[src] = 0;
 
    for (int i = 0; i < V-1; i++) {
        bool changes = false;
        for (int e = 0; e < E; e++) {
            int u = graph->edges[e].src;
            int v = graph->edges[e].dest;
            int weight = graph->edges[e].weight;
            if (dist[u] != INT_MAX && dist[u] + weight < dist[v]) {
                dist[v] = dist[u] + weight;
                changes = true;
            }
        }
        if (!changes) break;
    }

    return dist;
}
 
int main() {
    int V, F, E, subsidiary, u, v, w;

/****** BEGINNING - READS INPUT ******/
    cin >> V;
    cin >> F;
    cin >> E;

    struct Graph* graph = (struct Graph*) malloc(sizeof(struct Graph));
    graph->V = V;
    graph->E = E;
    graph->edges = (struct Edge*) malloc(graph->E * sizeof(struct Edge));


    vector<int> subsidiaries(F);

    for (int i = 0; i < F; i++) {
        cin >> subsidiary;
        subsidiaries[i] = subsidiary-1;
    }

    for(int i = 0; i < E; i++) {
        cin >> u;
        cin >> v;
        cin >> w;

        graph->edges[i].src = u-1;
        graph->edges[i].dest = v-1;
        graph->edges[i].weight = w;
    }
/****** END - READS INPUT  ******/


    vector< vector<int> > allDist(F, vector<int>(1));
    vector<int> totalDist(V);

    for(int i = 0; i < V; i++) {
        totalDist[i] = INT_MAX; 
    }
    
    for (int i = 0; i < F; i++) {
        allDist[i] = bellmanFord(graph, subsidiaries[i]);
    }


/****** BEGINNING - PROCESS OUTPUT ******/
    for (int i = 0; i < V; i++) {
        for(int y = 0; y < F; y++) {
            if (allDist[y][i] == INT_MAX) {
                totalDist[i] = INT_MAX;
                break;
            }
            else if(totalDist[i] != INT_MAX) 
                totalDist[i] += allDist[y][i];
            else 
                totalDist[i] = allDist[y][i];
        }
    } 

    int meetPoint = 0;

    for(int i = 0; i < V ; i++) {
        if(totalDist[i] < totalDist[meetPoint]) 
            meetPoint = i;
    }

    int meetPointDist = totalDist[meetPoint];
    
    if (meetPointDist != INT_MAX) {
        cout << (meetPoint + 1) << " " << meetPointDist << endl;

        for (int i = 0; i < F; i++) {
            cout << allDist[i][meetPoint] << " ";
        }
        cout << endl;
    }
    else cout << "N" << endl;
/****** END - PROCESS OUTPUT ******/

    return 0;
}