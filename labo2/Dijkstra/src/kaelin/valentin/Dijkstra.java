package kaelin.valentin;

import graph.core.impl.Digraph;
import graph.core.impl.SimpleWeightedEdge;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.stream.Stream;

public class Dijkstra {
    private final Digraph<SimpleVertex, SimpleWeightedEdge<SimpleVertex>> graph;
    
    public class AlgorithmData {
        public final PriorityQueue<SimpleVertex> verticesQueue;
        public final boolean[] queueContained;
        public final Long[] distances;
        public final Integer[] predecessors;
        
        public AlgorithmData(SimpleVertex startVertex, SimpleVertex endVertex) {
            if (startVertex == null)
                throw new RuntimeException("Sommet de départ invalide!");
            if (endVertex == null)
                throw new RuntimeException("Sommet d'arrivée invalide!");
            
            int nbVertices = graph.getNVertices();
            queueContained = new boolean[nbVertices];
            Arrays.fill(queueContained, true);
            distances = new Long[nbVertices];
            Arrays.fill(distances, Long.MAX_VALUE);
            predecessors = new Integer[nbVertices];
            Arrays.fill(predecessors, null);
            verticesQueue = new PriorityQueue<>(
                    graph.getNVertices(),
                    Comparator.comparingLong(v -> distances[v.id()])
            );
            verticesQueue.addAll(graph.getVertices());
            distances[startVertex.id()] = 0L;
        }
    }

//    private PriorityQueue<SimpleVertex> forwardQueue;
//    private PriorityQueue<SimpleVertex> backwardQueue;
//    private boolean[] forwardContained;
//    private boolean[] backwardContained;
//
//    private Long[] distancesFront;
//    private Long[] distancesBack;
    
    public Dijkstra(Digraph<SimpleVertex, SimpleWeightedEdge<SimpleVertex>> graph) {
        this.graph = graph;
    }
    
    public AlgorithmData runForward(SimpleVertex startVertex, SimpleVertex endVertex) {
        AlgorithmData forward = new AlgorithmData(startVertex, endVertex);
        
        while (!forward.verticesQueue.isEmpty()) {
            SimpleVertex nextVertex = forward.verticesQueue.remove();
            
            if (nextVertex.id() == endVertex.id() || forward.distances[nextVertex.id()] == Long.MAX_VALUE)
                break;
            
            findMin(forward, nextVertex);
        }
        
        return forward;
    }
    
    private void findMin(AlgorithmData data,
                         SimpleVertex nextVertex) {
        var successors = graph.getSuccessorList(nextVertex.id());
        for (SimpleWeightedEdge<SimpleVertex> list : successors) {
            SimpleVertex succ = list.to();
            if (data.queueContained[succ.id()] &&
                    (data.distances[succ.id()] > data.distances[nextVertex.id()] + list.weight())) {
                data.distances[succ.id()] = data.distances[nextVertex.id()] + list.weight();
                data.predecessors[succ.id()] = nextVertex.id();
                
                // Mise à jour de la liste de priorité
                data.verticesQueue.add(nextVertex);
                data.verticesQueue.remove(nextVertex);
            }
        }
    }
    
    public Long runBidirectional(SimpleVertex startVertex, SimpleVertex endVertex) {
        AlgorithmData forward = new AlgorithmData(startVertex, endVertex);
        AlgorithmData backward = new AlgorithmData(endVertex, startVertex);
        Long mu = Long.MAX_VALUE;
        
        // TODO
        while (!forward.verticesQueue.isEmpty() && !backward.verticesQueue.isEmpty()) {
            // Forward
            SimpleVertex nextVertex = forward.verticesQueue.remove();
            forward.queueContained[nextVertex.id()] = false;
            
            if (!backward.queueContained[nextVertex.id()])
                break;
            
            findMin(forward, nextVertex);

//            var successors = graph.getSuccessorList(nextVertex.id());
//            for (SimpleWeightedEdge<SimpleVertex> list : successors) {
//                SimpleVertex succ = list.to();
//                if (forwardContained[succ.id()] &&
//                        (distancesFront[succ.id()] > distancesFront[nextVertex.id()] + list.weight())) {
//                    distancesFront[succ.id()] = distancesFront[nextVertex.id()] + list.weight();
//                    predecessors[succ.id()] = nextVertex.id();
//
//                    // Mise à jour de la liste de priorité
//                    forward.verticesQueue.add(nextVertex);
//                    forward.verticesQueue.remove(nextVertex);
//
//                    // TODO: Mise à jour de mu
////                    if () {
////
////                    }
//                    mu = Math.min(mu, distancesFront[succ.id()]);
//                }
//            }
//            if (distancesBack[nextVertex.id()] != Long.MAX_VALUE) {
//                mu = Math.min(mu, distancesFront[nextVertex.id()]);
//            }
            
            
            // Backward
            // Backward
            nextVertex = backward.verticesQueue.remove();
            backward.queueContained[nextVertex.id()] = false;
            
            if (forward.queueContained[nextVertex.id()])
                break;
            
            findMin(backward, nextVertex);

//            successors = graph.getSuccessorList(nextVertex.id());
//            for (SimpleWeightedEdge<SimpleVertex> list : successors) {
//                SimpleVertex succ = list.to();
//                if (backwardContained[succ.id()] &&
//                        (distancesBack[succ.id()] > distancesBack[nextVertex.id()] + list.weight())) {
//                    distancesBack[succ.id()] = distancesBack[nextVertex.id()] + list.weight();
//                    predecessors[succ.id()] = nextVertex.id();
//
//                    // Mise à jour de la liste de priorité
//                    backwardQueue.add(nextVertex);
//                    backwardQueue.remove(nextVertex);
//
//                    mu = Math.min(mu, distancesBack[succ.id()]);
//                }
//            }
        }
        
        return mu;
    }
//
//    public Long[] getDistances() {
//        return distances;
//    }

//    public Long[] getEntireDistances() {
//        return Stream.concat(Arrays.stream(distancesFront), Arrays.stream(distancesBack))
//                .toArray(Long[]::new);
//    }

//    public Integer[] getPredecessors() {
//        return predecessors;
//    }
}
