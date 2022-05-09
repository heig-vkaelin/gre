package kaelin.valentin;

import graph.core.impl.Digraph;
import graph.core.impl.SimpleWeightedEdge;

import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.stream.Stream;

public class Dijkstra {
    private final Digraph<SimpleVertex, SimpleWeightedEdge<SimpleVertex>> graph;
    
    public Long mu;
    public SimpleWeightedEdge<SimpleVertex> muEdge;
    
    public class AlgorithmData {
        public final PriorityQueue<SimpleVertex> verticesQueue;
        public final boolean[] queueContained;
        public final Long[] distances;
        public final Integer[] predecessors;
        public int counter;
        
        public AlgorithmData(SimpleVertex from) {
            if (from == null)
                throw new RuntimeException("Sommet invalide!");
            
            counter = 0;
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
            distances[from.id()] = 0L;
            verticesQueue.addAll(graph.getVertices());
            
            mu = Long.MAX_VALUE;
            muEdge = null;
        }
    }
    
    public Dijkstra(Digraph<SimpleVertex, SimpleWeightedEdge<SimpleVertex>> graph) {
        this.graph = graph;
    }
    
    public AlgorithmData runForward(SimpleVertex from, SimpleVertex to) {
        AlgorithmData forward = new AlgorithmData(from);
        
        while (!forward.verticesQueue.isEmpty()) {
            SimpleVertex nextVertex = forward.verticesQueue.remove();
            forward.queueContained[nextVertex.id()] = false;
            
            if (nextVertex.id() == to.id() || forward.distances[nextVertex.id()] == Long.MAX_VALUE)
                break;
            
            forward.counter++;
            findMin(forward, nextVertex);
        }
        
        return forward;
    }
    
    private void findMin(AlgorithmData forward, SimpleVertex nextVertex) {
        findMin(forward, nextVertex, null);
    }
    
    private void findMin(AlgorithmData current, SimpleVertex nextVertex,
                         AlgorithmData otherDirection) {
        var successors = graph.getSuccessorList(nextVertex.id());
        for (SimpleWeightedEdge<SimpleVertex> edge : successors) {
            SimpleVertex succ = edge.to();
            if (current.queueContained[succ.id()] &&
                    (current.distances[succ.id()] > current.distances[nextVertex.id()] + edge.weight())) {
                current.distances[succ.id()] =
                        current.distances[nextVertex.id()] + edge.weight();
                current.predecessors[succ.id()] = nextVertex.id();
                
                // Mise à jour de la liste de priorité
                current.verticesQueue.remove(succ);
                current.verticesQueue.add(succ);
                
                // Mise à jour de mu
                if (otherDirection != null) {
                    if (!otherDirection.queueContained[succ.id()]
                            && mu >
                            current.distances[nextVertex.id()] + otherDirection.distances[succ.id()] + edge.weight()) {
                        System.out.println("before Mu: " + mu);
                        
                        mu = current.distances[nextVertex.id()]
                                + otherDirection.distances[succ.id()] + edge.weight();
                        muEdge = edge;
                        
                        System.out.println("Mu: " + mu);
                        System.out.println("MuEdge: " + muEdge.from().id() + " -> " + muEdge.to().id());
                    }
                }
            }
        }
    }
    
    public Long runBidirectional(SimpleVertex from, SimpleVertex to) {
        AlgorithmData forward = new AlgorithmData(from);
        AlgorithmData backward = new AlgorithmData(to);
//        Long mu = Long.MAX_VALUE;
//        SimpleWeightedEdge<SimpleVertex> muEdge = null;
        
        while (!forward.verticesQueue.isEmpty() && !backward.verticesQueue.isEmpty()) {
            // Forward
            SimpleVertex nextVertex = forward.verticesQueue.remove();
            forward.queueContained[nextVertex.id()] = false;
            
            if (!backward.queueContained[nextVertex.id()])
                break;
            
            ++forward.counter;
            findMin(forward, nextVertex, backward);
            
            // Backward
            nextVertex = backward.verticesQueue.remove();
            backward.queueContained[nextVertex.id()] = false;
            
            if (!forward.queueContained[nextVertex.id()])
                break;
            
            ++backward.counter;
            findMin(backward, nextVertex, forward);

//            mu = Math.min(mu, distancesFront[succ.id()]);
//                }
//            }
//            if (distancesBack[nextVertex.id()] != Long.MAX_VALUE) {
//                mu = Math.min(mu, distancesFront[nextVertex.id()]);
//            }
        }
        
        System.out.println("Mu final: " + mu);
        System.out.println("MuEdge final: " + muEdge.from().id() + " -> " + muEdge.to().id());
        
        System.out.println("Distances: ");

//        Long[] distances = new Long[forward.distances.length];
//        Integer[] predecessors = new Integer[forward.predecessors.length];
//        for (int i = 0; i < forward.distances.length; i++) {
//            if (forward.distances[i] < backward.distances[i]) {
//                distances[i] = forward.distances[i];
//                predecessors[i] = forward.predecessors[i];
//            } else {
//                distances[i] = backward.distances[i];
//                predecessors[i] = backward.predecessors[i];
//            }
//        }
        
        System.out.println(Arrays.toString(forward.distances));
        System.out.println(Arrays.toString(backward.distances));
        System.out.println("Prédécesseurs");
        System.out.println(Arrays.toString(forward.predecessors));
        System.out.println(Arrays.toString(backward.predecessors));
        
        // TODO: remove code en dessous et facto la method pour print
//        int fromId = from.id();
//        int toId = to.id();
//        LinkedList<Integer> path = new LinkedList<>();
//
//        while (fromId != toId) {
//            path.addFirst(toId);
//            toId = predecessors[toId];
//        }
//        path.addFirst(fromId);
//
//        System.out.println(path);
//        System.out.println("Total distance: " + distances[to.id()]);
        System.out.println("Nombre de sommets traités: " + (forward.counter + backward.counter));
        printPath2(forward, backward, from, to, muEdge);
        
        // fin du code à supprimer
        
        return Long.MAX_VALUE;
    }
    
    public void printPath2(AlgorithmData forward, AlgorithmData backward,
                           SimpleVertex from, SimpleVertex to,
                           SimpleWeightedEdge<SimpleVertex> muEdge) {
        int sourceId = from.id();
        int destId = to.id();
        int fromId = muEdge.from().id();
        int toId = muEdge.to().id();
        LinkedList<Integer> path = new LinkedList<>();
        while (fromId != sourceId && fromId != destId) {
            path.addFirst(fromId);
            fromId = forward.predecessors[fromId];
        }
        path.addFirst(sourceId);
        
        while (toId != destId && toId != sourceId) {
            path.addLast(toId);
            toId = backward.predecessors[toId];
        }
        path.addLast(destId);
        
        System.out.println("Chemin: " + path);
    }
    
    public void printPath(AlgorithmData data, SimpleVertex from, SimpleVertex to) {
        int fromId = from.id();
        int toId = to.id();
        LinkedList<Integer> path = new LinkedList<>();
        
        while (fromId != toId) {
            path.addFirst(toId);
            toId = data.predecessors[toId];
        }
        path.addFirst(fromId);
        
        System.out.println(path);
        System.out.println("Total distance: " + data.distances[to.id()]);
        System.out.println("Nombre de sommets traités: " + data.counter);
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
