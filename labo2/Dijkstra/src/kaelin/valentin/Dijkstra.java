package kaelin.valentin;

import graph.core.impl.Digraph;
import graph.core.impl.SimpleWeightedEdge;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.stream.Stream;

public class Dijkstra {
    private final Digraph<SimpleVertex, SimpleWeightedEdge<SimpleVertex>> graph;
    private PriorityQueue<SimpleVertex> verticesToVisit;
    private PriorityQueue<SimpleVertex> forwardQueue;
    private PriorityQueue<SimpleVertex> backwardQueue;
    private boolean[] forwardContained;
    private boolean[] backwardContained;
    
    private Long[] distances;
    private Long[] distancesFront;
    private Long[] distancesBack;
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
    
    public Long runBidirectional(SimpleVertex startVertex, SimpleVertex endVertex) {
        if (startVertex == null || endVertex == null)
            throw new RuntimeException("Sommet de départ ou d'arrivée invalide!");
        
        int nbVertices = graph.getNVertices();
        forwardContained = new boolean[nbVertices];
        backwardContained = new boolean[nbVertices];
        Arrays.fill(forwardContained, true);
        Arrays.fill(backwardContained, true);
        distancesFront = new Long[nbVertices];
        Arrays.fill(distancesFront, Long.MAX_VALUE);
        distancesBack = new Long[nbVertices];
        Arrays.fill(distancesBack, Long.MAX_VALUE);
        predecessors = new Integer[nbVertices];
        Arrays.fill(predecessors, null);
        distancesFront[startVertex.id()] = 0L;
        distancesBack[endVertex.id()] = 0L;
        forwardQueue = new PriorityQueue<>(
                graph.getNVertices(),
                Comparator.comparingLong(v -> distancesFront[v.id()])
        );
        forwardQueue.addAll(graph.getVertices());
        backwardQueue = new PriorityQueue<>(
                graph.getNVertices(),
                Comparator.comparingLong(v -> distancesBack[v.id()])
        );
        backwardQueue.addAll(graph.getVertices());
        Long mu = Long.MAX_VALUE;
        
        // TODO
        while (!forwardQueue.isEmpty() && !backwardQueue.isEmpty()) {
            // Forward
            SimpleVertex nextVertex = forwardQueue.remove();
            forwardContained[nextVertex.id()] = false;
            
            if (!backwardContained[nextVertex.id()])
                break;
            
            var successors = graph.getSuccessorList(nextVertex.id());
            for (SimpleWeightedEdge<SimpleVertex> list : successors) {
                SimpleVertex succ = list.to();
                if (forwardContained[succ.id()] &&
                        (distancesFront[succ.id()] > distancesFront[nextVertex.id()] + list.weight())) {
                    distancesFront[succ.id()] = distancesFront[nextVertex.id()] + list.weight();
                    predecessors[succ.id()] = nextVertex.id();
                    
                    // Mise à jour de la liste de priorité
                    forwardQueue.add(nextVertex);
                    forwardQueue.remove(nextVertex);
                    
                    // TODO: Mise à jour de mu
//                    if () {
//
//                    }
                    mu = Math.min(mu, distancesFront[succ.id()]);
                }
            }
//            if (distancesBack[nextVertex.id()] != Long.MAX_VALUE) {
//                mu = Math.min(mu, distancesFront[nextVertex.id()]);
//            }
            
            
            // Backward
            nextVertex = backwardQueue.remove();
            backwardContained[nextVertex.id()] = false;
    
            if (forwardContained[nextVertex.id()])
                break;
            
            successors = graph.getSuccessorList(nextVertex.id());
            for (SimpleWeightedEdge<SimpleVertex> list : successors) {
                SimpleVertex succ = list.to();
                if (backwardContained[succ.id()] &&
                        (distancesBack[succ.id()] > distancesBack[nextVertex.id()] + list.weight())) {
                    distancesBack[succ.id()] = distancesBack[nextVertex.id()] + list.weight();
                    predecessors[succ.id()] = nextVertex.id();
                    
                    // Mise à jour de la liste de priorité
                    backwardQueue.add(nextVertex);
                    backwardQueue.remove(nextVertex);
    
                    mu = Math.min(mu, distancesBack[succ.id()]);
                }
            }
        }
        
        return mu;
    }
    
    public Long[] getDistances() {
        return distances;
    }
    
    public Long[] getEntireDistances() {
        return Stream.concat(Arrays.stream(distancesFront), Arrays.stream(distancesBack))
                .toArray(Long[]::new);
    }
    
    public Integer[] getPredecessors() {
        return predecessors;
    }
}
