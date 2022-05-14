
/*
 * Le code rendu se situe uniquement dans ce package (event. sous-packages)
 */
package kaelin.valentin;

import graph.core.impl.SimpleWeightedEdgeFactory;
import graph.reader.CartesianGraphReader;

import java.io.IOException;

/**
 * Point d'entrée du programme, compare l'algorithme de Dijkstra avec sa variante
 * bidirectionnelle
 *
 * @author Valentin Kaelin
 */
public class Main {
    private static final String DATA_FOLDER = "data/";
    private static final String SMALL_GRAPH = "R15_1.txt";
    private static final String MEDIUM_GRAPH = "R10000_1.txt";
    private static final String LARGE_GRAPH = "R50000_1.txt";
    
    public static void main(String[] args) throws IOException {
        var graph = new CartesianGraphReader<>(
                new SimpleVertexFactory(),
                new SimpleWeightedEdgeFactory<>(new SimpleEdgeWeighter()),
                DATA_FOLDER + SMALL_GRAPH
        ).graph();
        
        SimpleVertex from = graph.getVertices().get(5);
        SimpleVertex to = graph.getVertices().get(9);
        
        System.out.println("FORWARD:");
        Dijkstra forward = new Dijkstra(graph);
        Results results = forward.runForward(from, to);
        
        System.out.println("Path: " + results.getShortestPath());
        System.out.println("Nb sommets traités: " + results.getNbVerticesProcessed());
        
        System.out.println("\nBIDIRECTIONAL:");
        Dijkstra bidirectional = new Dijkstra(graph);
        Results resultsBi = bidirectional.runBidirectional(from, to);
        
        System.out.println("Path: " + resultsBi.getShortestPath());
        System.out.println("Nb sommets traités: " + resultsBi.getNbVerticesProcessed());
    }
}
