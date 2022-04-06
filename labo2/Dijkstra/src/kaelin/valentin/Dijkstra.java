package kaelin.valentin;

import graph.core.impl.Digraph;
import graph.core.impl.SimpleWeightedEdge;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.stream.Collectors;

public class Dijkstra {
    private final Digraph<SimpleVertex, SimpleWeightedEdge<SimpleVertex>> graph;
    private PriorityQueue<SimpleVertex> verticesToVisit;
    
    private long[] distances;
    private SimpleVertex[] predecessors;
    
    public Dijkstra(Digraph<SimpleVertex, SimpleWeightedEdge<SimpleVertex>> graph) {
        this.graph = graph;
    }
    
    public void run(int startVertex) {
        int nbVertices = this.graph.getNVertices();
        if (startVertex > nbVertices || startVertex < 0)
            throw new RuntimeException("Sommet de départ invalide!");
        
        distances = new long[nbVertices];
        Arrays.fill(distances, Long.MAX_VALUE);
        predecessors = new SimpleVertex[nbVertices];
        Arrays.fill(predecessors, null);
        verticesToVisit = new PriorityQueue<>(
                this.graph.getNVertices(),
                Comparator.comparingLong(v -> distances[v.id()])
        );
        verticesToVisit.addAll(this.graph.getVertices());
        distances[startVertex] = 0;
        
        while (!verticesToVisit.isEmpty()) {
            SimpleVertex nextVertex = verticesToVisit.remove();
            
            if (distances[nextVertex.id()] == Long.MAX_VALUE)
                continue;
            
            var successors = this.graph.getSuccessorList(nextVertex.id());
            
            for (SimpleWeightedEdge<SimpleVertex> succ : successors) {
                SimpleVertex j = succ.to();
                if (verticesToVisit.contains(j) &&
                        (distances[j.id()] > distances[nextVertex.id()] + succ.weight())) {
                    distances[j.id()] = distances[nextVertex.id()] + succ.weight();
                    predecessors[j.id()] = nextVertex;
                }
            }
        }
    }
    
    public long[] getDistances() {
        return distances;
    }
    
    public SimpleVertex[] getPredecessors() {
        return predecessors;
    }
}
