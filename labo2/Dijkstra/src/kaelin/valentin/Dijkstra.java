package kaelin.valentin;

import graph.core.impl.Digraph;
import graph.core.impl.SimpleWeightedEdge;

import java.util.PriorityQueue;

public class Dijkstra {
    private final Digraph<SimpleVertex, SimpleWeightedEdge<SimpleVertex>> graph;
    
    public long[] getDistances() {
        return distances;
    }
    
    public Integer[] getPredecessors() {
        return predecessors;
    }
    
    private long[] distances;
    private Integer[] predecessors;
    
    public Dijkstra(Digraph<SimpleVertex, SimpleWeightedEdge<SimpleVertex>> graph) {
        this.graph = graph;
    }
    
    public void run(int startVertex) {
        int nbVertices = this.graph.getNVertices();
        if (startVertex > nbVertices)
            throw new RuntimeException("Sommet de départ invalide!");
        
        distances = new long[nbVertices];
        predecessors = new Integer[nbVertices];
        PriorityQueue<Integer> verticesToVisit = new PriorityQueue<>();
        
        for (int i = 0; i < nbVertices; i++) {
            distances[i] = Long.MAX_VALUE;
            predecessors[i] = null;
            verticesToVisit.add(i);
        }
        distances[startVertex] = 0;
        
        while (!verticesToVisit.isEmpty()) {
            int nextVertex = verticesToVisit.poll();
            
            if (distances[nextVertex] == Long.MAX_VALUE)
                break;
            
            // TODO: transformer les successeurs en vertex et pas en edge
            var successors = this.graph.getSuccessorList(nextVertex);
            
            for (var succ : successors) {
                int j = succ.to().id();
                if (verticesToVisit.contains(j) &&
                        distances[j] > distances[nextVertex] + succ.weight()) {
                    distances[j] = distances[nextVertex] + succ.weight();
                    predecessors[j] = nextVertex;
                }
            }
        }
    }
}
