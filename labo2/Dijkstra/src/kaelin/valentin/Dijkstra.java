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
        int nbVertices = graph.getNVertices();
        if (startVertex > nbVertices || startVertex < 0)
            throw new RuntimeException("Sommet de départ invalide!");
        
        distances = new long[nbVertices];
        Arrays.fill(distances, Long.MAX_VALUE);
        predecessors = new SimpleVertex[nbVertices];
        Arrays.fill(predecessors, null);
        verticesToVisit = new PriorityQueue<>(
                graph.getNVertices(),
                Comparator.comparingLong(v -> distances[v.id()])
        );
        verticesToVisit.addAll(graph.getVertices());
        distances[startVertex] = 0;
        
        while (!verticesToVisit.isEmpty()) {
            SimpleVertex nextVertex = verticesToVisit.remove();
            
            if (distances[nextVertex.id()] == Long.MAX_VALUE)
                continue;
            
            var successors = graph.getSuccessorList(nextVertex.id());
            for (SimpleWeightedEdge<SimpleVertex> list : successors) {
                SimpleVertex succ = list.to();
                if (verticesToVisit.contains(succ) &&
                        (distances[succ.id()] > distances[nextVertex.id()] + list.weight())) {
                    distances[succ.id()] = distances[nextVertex.id()] + list.weight();
                    // Mise à jour de la liste de priorité
                    verticesToVisit.remove(succ);
                    verticesToVisit.add(succ);
                    predecessors[succ.id()] = nextVertex;
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
