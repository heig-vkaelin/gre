package kaelin.valentin;

import graph.core.impl.Digraph;
import graph.core.impl.SimpleWeightedEdge;
import graph.core.impl.SimpleWeightedEdgeFactory;
import graph.reader.CartesianGraphReader;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Point d'entrée du programme, compare l'algorithme de Dijkstra avec sa variante
 * bidirectionnelle
 *
 * @author Valentin Kaelin
 */
public class Main {
    private static final int SEED = 20220404;
    private static final Random RANDOM = new Random(SEED);
    private static final String DATA_FOLDER = "data/";
    private static final String SMALL_GRAPH = "R15_1.txt";
    private static final String MEDIUM_GRAPH = "R10000_1.txt";
    private static final String LARGE_GRAPH = "R50000_1.txt";
    
    public static void main(String[] args) throws IOException {
        var graph = new CartesianGraphReader<>(
                new SimpleVertexFactory(),
                new SimpleWeightedEdgeFactory<>(new SimpleEdgeWeighter()),
                DATA_FOLDER + MEDIUM_GRAPH
        ).graph();
        
        testAlgorithms(graph);

//        SimpleVertex from = graph.getVertices().get(5);
//        SimpleVertex to = graph.getVertices().get(9);
//
//        System.out.println("FORWARD:");
//        Dijkstra forward = new Dijkstra(graph);
//        Results results = forward.runForward(from, to);
//
//        System.out.println("Path: " + results.getShortestPath());
//        System.out.println("Nb sommets traités: " + results.getNbVerticesProcessed());
//
//        System.out.println("\nBIDIRECTIONAL:");
//        Dijkstra bidirectional = new Dijkstra(graph);
//        Results resultsBi = bidirectional.runBidirectional(from, to);
//
//        System.out.println("Path: " + resultsBi.getShortestPath());
//        System.out.println("Nb sommets traités: " + resultsBi.getNbVerticesProcessed());
    }
    
    /**
     * Lance les tests souhaités pour comparer les deux versions de Dijkstra
     *
     * @param graph : graphe sur lequel appliquer l'algorithme
     * @throws FileNotFoundException en cas d'erreur lors de l'écriture du fichier
     */
    private static void testAlgorithms(
            Digraph<SimpleVertex, SimpleWeightedEdge<SimpleVertex>> graph)
            throws FileNotFoundException {
        final int NB_TESTS = 1000;
        final int NB_VERTICES = 10000;
        List<Integer> sources = new LinkedList<>();
        List<Integer> targets = new LinkedList<>();
        List<Integer> nbVerticesClassic = new LinkedList<>();
        List<Integer> nbVerticesBidirectional = new LinkedList<>();
        
        Dijkstra dijkstra = new Dijkstra(graph);
        
        // Lance les tests
        System.out.println("Démarrage des " + NB_TESTS + " tests");
        for (int i = 0; i < NB_TESTS; i++) {
            int fromId = RANDOM.nextInt(NB_VERTICES);
            int toId = RANDOM.nextInt(NB_VERTICES);
            sources.add(fromId);
            targets.add(toId);
            
            SimpleVertex from = graph.getVertices().get(fromId);
            SimpleVertex to = graph.getVertices().get(toId);
            
            Results classic = dijkstra.runForward(from, to);
            nbVerticesClassic.add(classic.getNbVerticesProcessed());
            Results bidirectional = dijkstra.runBidirectional(from, to);
            nbVerticesBidirectional.add(bidirectional.getNbVerticesProcessed());
        }
        
        // Sauvegarde les résultats dans un fichier
        System.out.println("Tests terminés, sauvegarde dans un fichier en cours.");
        PrintStream out = new PrintStream(new FileOutputStream("results.csv"));
        out.println("#, Source, Destination, Nb sommets traités classique, Nb sommets traités bidirectionnel");
        for (int i = 0; i < NB_TESTS; i++) {
            out.println(Stream.of(i + 1, sources.get(i), targets.get(i),
                            nbVerticesClassic.get(i), nbVerticesBidirectional.get(i))
                    .map(String::valueOf).collect(Collectors.joining(","))
            );
        }
        out.close();
    }
}
