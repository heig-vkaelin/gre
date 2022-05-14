package kaelin.valentin;

import graph.core.impl.Digraph;
import graph.core.impl.SimpleWeightedEdge;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;

/**
 * Classe permettant d'appliquer l'algorithme de Dijkstra (bidirectionnel ou non)
 * au graphe souhaité.
 *
 * @author Valentin Kaelin
 */
public class Dijkstra {
    private final Digraph<SimpleVertex, SimpleWeightedEdge<SimpleVertex>> graph;
    
    private Long mu;
    private SimpleWeightedEdge<SimpleVertex> muEdge;
    
    private class AlgorithmData {
        private final PriorityQueue<SimpleVertex> verticesQueue;
        private final boolean[] queueContained;
        private final Long[] distances;
        private final Integer[] predecessors;
        private int counter;
        
        public AlgorithmData(SimpleVertex from) {
            if (from == null)
                throw new RuntimeException("Sommet invalide!");
            
            counter = 0;
            
            int nbVertices = graph.getNVertices();
            queueContained = new boolean[nbVertices];
            distances = new Long[nbVertices];
            predecessors = new Integer[nbVertices];
            
            for (int i = 0; i < nbVertices; i++) {
                queueContained[i] = true;
                distances[i] = Long.MAX_VALUE;
                predecessors[i] = null;
            }
            
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
    
    /**
     * Crée l'instance de la classe en stockant le graphe souhaité pour pouvoir
     * par la suite appliquer l'algorithme
     *
     * @param graph : graphe souhaité
     */
    public Dijkstra(Digraph<SimpleVertex, SimpleWeightedEdge<SimpleVertex>> graph) {
        this.graph = graph;
    }
    
    /**
     * Exécute l'algorithme de Dijkstra classique
     *
     * @param from : sommet de départ
     * @param to   : sommet d'arrivée
     * @return les résultats de l'algorithme (chemin le plus court et nombre de
     * sommets traités)
     */
    public Results runForward(SimpleVertex from, SimpleVertex to) {
        AlgorithmData forward = new AlgorithmData(from);
        
        while (!forward.verticesQueue.isEmpty()) {
            SimpleVertex nextVertex = forward.verticesQueue.remove();
            forward.queueContained[nextVertex.id()] = false;
            
            if (nextVertex.id() == to.id() || forward.distances[nextVertex.id()] == Long.MAX_VALUE)
                break;
            
            forward.counter++;
            findMin(forward, nextVertex);
        }
        
        return new Results(getPath(forward, from.id(), to.id()), forward.counter);
    }
    
    /**
     * Exécute l'algorithme de Dijkstra bidirectionnel
     *
     * @param from : sommet de départ
     * @param to   : sommet d'arrivée
     * @return les résultats de l'algorithme (chemin le plus court et nombre de
     * sommets traités)
     */
    public Results runBidirectional(SimpleVertex from, SimpleVertex to) {
        AlgorithmData forward = new AlgorithmData(from);
        AlgorithmData backward = new AlgorithmData(to);
        
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
        }

//        System.out.println("Mu final: " + mu);
//        System.out.println("MuEdge final: " + muEdge.from().id() + " -> " + muEdge.to().id());
        
        return new Results(
                getBiDirectionalPath(forward, backward, from.id(), to.id(), muEdge),
                forward.counter + backward.counter
        );
    }
    
    /**
     * Traite un sommet lors de l'algorithme de Dijkstra dans sa version
     * classique
     *
     * @param forward    : données de l'exploration en avant
     * @param nextVertex : sommet à traiter
     */
    private void findMin(AlgorithmData forward, SimpleVertex nextVertex) {
        findMin(forward, nextVertex, null);
    }
    
    /**
     * Traite un sommet lors de l'algorithme de Dijkstra dans sa version
     * bidirectionnelle
     *
     * @param current        : données de l'exploration dans le sens actuel
     * @param nextVertex     : sommet à traiter
     * @param otherDirection : données de l'exploration dans l'autre sens
     */
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
                
                // Mise à jour de mu si bidirectionnel
                if (otherDirection != null && !otherDirection.queueContained[succ.id()]
                        && mu >
                        current.distances[nextVertex.id()] + otherDirection.distances[succ.id()] + edge.weight()) {
                    mu = current.distances[nextVertex.id()] + otherDirection.distances[succ.id()] + edge.weight();
                    muEdge = edge;
//                    System.out.println("Mu: " + mu);
//                    System.out.println("MuEdge: " + muEdge.from().id() + " -> " + muEdge.to().id());
                }
            }
        }
    }
    
    /**
     * Retourne le plus court chemin entre 2 sommets sous la forme d'une liste
     * après avoir effectué un Dijkstra bidirectionnel
     *
     * @param forward  : données de l'exploration en avant
     * @param backward : données de l'exploration en arrière
     * @param sourceId : id du sommet de départ
     * @param destId   : id du sommet d'arrivée
     * @param muEdge   : dernier arc stocké lors de la modification de mu
     * @return le plus court chemin
     */
    private LinkedList<Integer> getBiDirectionalPath(AlgorithmData forward,
                                                     AlgorithmData backward,
                                                     int sourceId, int destId,
                                                     SimpleWeightedEdge<SimpleVertex> muEdge) {
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
        
        return path;
    }
    
    /**
     * Retourne le plus court chemin entre 2 sommets sous la forme d'une liste
     * après avoir effectué un Dijkstra classique
     *
     * @param data : données de l'exploration en avant
     * @param from : id du sommet de départ
     * @param to   : id du sommet d'arrivée
     * @return le plus court chemin
     */
    private LinkedList<Integer> getPath(AlgorithmData data, int from, int to) {
        LinkedList<Integer> path = new LinkedList<>();
        
        while (from != to) {
            path.addFirst(to);
            to = data.predecessors[to];
        }
        path.addFirst(from);
        
        return path;
    }
}
