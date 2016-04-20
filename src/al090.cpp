/**************************************************** 
**** [LEIC-A] Analysis and Synthesis of Algorithms
**** 2nd Project
**** 04/2016
*****************************************************
**** Rafael Martins, 82034
**** Rafael Nilton, 426866 
*****************************************************/

#include <iostream> 
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <limits.h>
#include <vector>

using namespace std;
 
struct Edge
{
    short src, dest, weight;
};
 
struct Graph
{
    short V, E;
 
    struct Edge* edge;
};
 
struct Graph* createGraph(short V, short E)
{
    struct Graph* graph = (struct Graph*) malloc( sizeof(struct Graph) );
    graph->V = V;
    graph->E = E;
 
    graph->edge = (struct Edge*) malloc( graph->E * sizeof( struct Edge ) );
 
    return graph;
}
 

vector<short> bellmanFord(struct Graph* graph, short src)
{
    short V = graph->V;
    short E = graph->E;
    vector<short> dist(V);

    for (short i = 0; i < V; i++)
        dist[i]   = SHRT_MAX;
    dist[src] = 0;
 
    for (short i = 1; i <= V-1; i++)
    {
        for (short j = 0; j < E; j++)
        {
            short u = graph->edge[j].src;
            short v = graph->edge[j].dest;
            short weight = graph->edge[j].weight;
            if (dist[u] != SHRT_MAX && dist[u] + weight < dist[v])
                dist[v] = dist[u] + weight;
        }
    }
 
        return dist;
}
 
int main()
{
    short V, f, E, subsidiary, u, v, w;

// BEGINNING - READS INPUT 
    cin >> V;
    cin >> f;
    cin >> E;

    struct Graph* graph = createGraph(V, E);

    vector<short> subsidiaries(f);
    for (short i = 0; i < f; i++) {
        cin >> subsidiary;
        subsidiaries[i] = subsidiary-1;
    }

    for(short i = 0; i < E; i++) {
        cin >> u;
        cin >> v;
        cin >> w;

        graph->edge[i].src = u-1;
        graph->edge[i].dest = v-1;
        graph->edge[i].weight = w;
    }
// END - READS INPUT 
    vector< vector<short> > allDist(f, vector<short>(1));
    vector<short> totalDist(V);
    for(short i = 0; i < V; i++) {
        totalDist[i] = SHRT_MAX; 
    }
    
    for (short i = 0; i < f; i++) {
        allDist[i] = bellmanFord(graph, subsidiaries[i]);
    }
// BEGINNING - PROCESS OUTPUT
    for (short i = 0; i < V; i++) {
        for(short y = 0; y < f; y++) {
            if (allDist[y][i] == SHRT_MAX) {
                totalDist[i] = SHRT_MAX;
                break;
            }
            else if(totalDist[i] != SHRT_MAX) 
                totalDist[i] += allDist[y][i];
            else 
                totalDist[i] = allDist[y][i];
        }
    } 

    short meetPoint = 0;
    for(short i = 0; i < V ; i++) {
        if(totalDist[i] < totalDist[meetPoint]) meetPoint = i;
    }
    short meetPointDist = totalDist[meetPoint];
    
    if (meetPointDist != SHRT_MAX) {
        cout << (meetPoint + 1) << " " << meetPointDist << endl; // meeting poshort and respective total loss

        for (short i = 0; i < f; i++) {
            cout << allDist[i][meetPoint] << " ";
        }
        cout << endl;
    }
    else cout << "N" << endl;
// END - PROCESS OUTPUT

    return 0;
}