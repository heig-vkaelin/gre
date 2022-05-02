
/*
 * Le code rendu se situe uniquement dans ce package (event. sous-packages)
 */
package kaelin.valentin;

import graph.core.impl.SimpleWeightedEdgeFactory;
import graph.reader.CartesianGraphReader;

import java.io.IOException;
import java.util.Arrays;

public class Main {
    /*
     * NE PAS MODIFIER
     * Les fichiers de données sont à placer à la racine de ce répertoire
     */
    private static final String DATA_FOLDER = "data/";
    
    public static void main(String[] args) throws IOException {
        /* TODO: Fournir une fabrique de sommets (il s'agit d'une interface
            fonctionnelle) */
        
        /* TODO: Fournir une fonction de pondération renvoyant la distance
            euclidienne (arrondie à l'entier le plus proche) entre l'extrémité
            initiale et l'extrémité finale de l'arête */
        
        /* TODO: Chemin des fichiers */
        
        var graph = new CartesianGraphReader<>(
                new SimpleVertexFactory(),
                new SimpleWeightedEdgeFactory<>(new SimpleEdgeWeighter()),
                DATA_FOLDER + "R15_1.txt"
        ).graph();
        
        Dijkstra forward = new Dijkstra(graph);
        forward.runForward(graph.getVertices().get(0));
        
        System.out.println("\nFORWARD:");
        System.out.println("Distances: ");
        System.out.println(Arrays.toString(forward.getDistances()));
        System.out.println("Prédécesseurs");
        System.out.println(Arrays.toString(forward.getPredecessors()));
        
        
        Dijkstra bidirectional = new Dijkstra(graph);
        Long dist = bidirectional.runBidirectional(
                graph.getVertices().get(0),
                graph.getVertices().get(graph.getVertices().size() - 1)
        );
        System.out.println("\nBIDIRECTIONAL:");
        System.out.println("Distances: ");
        System.out.println(Arrays.toString(bidirectional.getEntireDistances()));
        System.out.println("Prédécesseurs");
        System.out.println(Arrays.toString(bidirectional.getPredecessors()));
        System.out.println("Distance de S à T: " + dist);

//        DigraphDijkstrazer dd = new DigraphDijkstrazer(graph);
//        dd.solve(graph.getVertices().get(0));
//        System.out.println("Distances: ");
//        System.out.println(Arrays.toString(dd.getLambdas()));
//        System.out.println("Prédécesseurs");
//        System.out.println(Arrays.toString(dd.getPreds()));
    }
}
