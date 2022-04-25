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
    
    private Long[] distances;
    private Integer[] predecessors;
    
    public Dijkstra(Digraph<SimpleVertex, SimpleWeightedEdge<SimpleVertex>> graph) {
        this.graph = graph;
    }
    
    public void run(SimpleVertex startVertex) {
        if (startVertex == null)
            throw new RuntimeException("Sommet de départ invalide!");
        
        int nbVertices = graph.getNVertices();
        distances = new Long[nbVertices];
        Arrays.fill(distances, Long.MAX_VALUE);
        predecessors = new Integer[nbVertices];
        Arrays.fill(predecessors, null);
        verticesToVisit = new PriorityQueue<>(
                graph.getNVertices(),
                Comparator.comparingLong(v -> distances[v.id()])
        );
        verticesToVisit.addAll(graph.getVertices());
        distances[startVertex.id()] = 0L;
        
        while (!verticesToVisit.isEmpty()) {
            SimpleVertex nextVertex = verticesToVisit.remove();
            
            if (distances[nextVertex.id()] == Long.MAX_VALUE)
                break;
            
            var successors = graph.getSuccessorList(nextVertex.id());
            for (SimpleWeightedEdge<SimpleVertex> list : successors) {
                SimpleVertex succ = list.to();
                if (verticesToVisit.contains(succ) &&
                        (distances[succ.id()] > distances[nextVertex.id()] + list.weight())) {
                    distances[succ.id()] = distances[nextVertex.id()] + list.weight();
                    // Mise à jour de la liste de priorité
                    verticesToVisit.remove(succ);
                    verticesToVisit.add(succ);
                    predecessors[succ.id()] = nextVertex.id();
                }
            }
        }
    }
    
    public Long[] getDistances() {
        return distances;
    }
    
    public Integer[] getPredecessors() {
        return predecessors;
    }
}
