package kaelin.valentin;

import graph.core.impl.Digraph;
import graph.core.impl.SimpleWeightedEdge;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

public class Dijkstra {
    private final Digraph<SimpleVertex, SimpleWeightedEdge<SimpleVertex>> graph;
    private PriorityQueue<SimpleVertex> verticesToVisit;
    private PriorityQueue<SimpleVertex> forwardQueue;
    private PriorityQueue<SimpleVertex> backwardQueue;
    
    private Long[] distances;
    private Integer[] predecessors;
    
    public Dijkstra(Digraph<SimpleVertex, SimpleWeightedEdge<SimpleVertex>> graph) {
        this.graph = graph;
    }
    
    public void runForward(SimpleVertex startVertex) {
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
                    predecessors[succ.id()] = nextVertex.id();
                    
                    // Mise à jour de la liste de priorité
                    verticesToVisit.add(nextVertex);
                    verticesToVisit.remove(nextVertex);
                }
            }
        }
    }
    
    public void runBidirectional(SimpleVertex startVertex, SimpleVertex endVertex) {
        if (startVertex == null || endVertex == null)
            throw new RuntimeException("Sommet de départ ou d'arrivée invalide!");
        
        int nbVertices = graph.getNVertices();
        distances = new Long[nbVertices];
        Arrays.fill(distances, Long.MAX_VALUE);
        predecessors = new Integer[nbVertices];
        Arrays.fill(predecessors, null);
        forwardQueue = new PriorityQueue<>(
                graph.getNVertices(),
                Comparator.comparingLong(v -> distances[v.id()])
        );
        forwardQueue.addAll(graph.getVertices());
        backwardQueue = new PriorityQueue<>(
                graph.getNVertices(),
                Comparator.comparingLong(v -> distances[v.id()])
        );
        backwardQueue.addAll(graph.getVertices());
        distances[startVertex.id()] = 0L;
        distances[endVertex.id()] = 0L;
        
        // TODO
        int i = 0;
        while (!forwardQueue.isEmpty() && !backwardQueue.isEmpty()) {
            // Forward
            if (i % 2 == 0) {
                SimpleVertex nextVertex = forwardQueue.remove();
    
                if (distances[nextVertex.id()] == Long.MAX_VALUE)
                    break;
    
                var successors = graph.getSuccessorList(nextVertex.id());
                for (SimpleWeightedEdge<SimpleVertex> list : successors) {
                    SimpleVertex succ = list.to();
                    if (forwardQueue.contains(succ) &&
                            (distances[succ.id()] > distances[nextVertex.id()] + list.weight())) {
                        distances[succ.id()] = distances[nextVertex.id()] + list.weight();
                        predecessors[succ.id()] = nextVertex.id();
            
                        // Mise à jour de la liste de priorité
                        forwardQueue.add(nextVertex);
                        forwardQueue.remove(nextVertex);
                    }
                }
            }
            // Backward
            else {
                SimpleVertex nextVertex = backwardQueue.remove();
    
                if (distances[nextVertex.id()] == Long.MAX_VALUE)
                    break;
    
                // TODO: predecessor à la place de successor ?
                var successors = graph.getSuccessorList(nextVertex.id());
                for (SimpleWeightedEdge<SimpleVertex> list : successors) {
                    SimpleVertex succ = list.from();
                    if (backwardQueue.contains(succ) &&
                            (distances[succ.id()] > distances[nextVertex.id()] + list.weight())) {
                        distances[succ.id()] = distances[nextVertex.id()] + list.weight();
                        predecessors[succ.id()] = nextVertex.id();
            
                        // Mise à jour de la liste de priorité
                        backwardQueue.add(nextVertex);
                        backwardQueue.remove(nextVertex);
                    }
                }
            }
            ++i;
        }
    }
    
    public Long[] getDistances() {
        return distances;
    }
    
    public Integer[] getPredecessors() {
        return predecessors;
    }
}
