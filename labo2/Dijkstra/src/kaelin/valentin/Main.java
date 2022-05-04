
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
        var graph = new CartesianGraphReader<>(
                new SimpleVertexFactory(),
                new SimpleWeightedEdgeFactory<>(new SimpleEdgeWeighter()),
                DATA_FOLDER + "R15_1.txt"
        ).graph();
        
        Dijkstra forward = new Dijkstra(graph);
        SimpleVertex from = graph.getVertices().get(9);
        SimpleVertex to = graph.getVertices().get(10);
        Dijkstra.AlgorithmData data = forward.runForward(from, to);
        
        System.out.println("FORWARD:");
        System.out.println("Distances: ");
        System.out.println(Arrays.toString(data.distances));
        System.out.println("Prédécesseurs");
        System.out.println(Arrays.toString(data.predecessors));
        
        System.out.print("Chemin le plus court: ");
        forward.printPath(data, from, to);
    
        System.out.println("\nBIDIRECTIONAL:");
        Dijkstra bidirectional = new Dijkstra(graph);
        Long dist = bidirectional.runBidirectional(
                graph.getVertices().get(9),
                graph.getVertices().get(10)
        );
//        System.out.println("Distances: ");
//        System.out.println(Arrays.toString(bidirectional.getEntireDistances()));
//        System.out.println("Prédécesseurs");
//        System.out.println(Arrays.toString(bidirectional.getPredecessors()));
//        System.out.println("Distance de S à T: " + dist);


//        DigraphDijkstrazer dd = new DigraphDijkstrazer(graph);
//        dd.solve(graph.getVertices().get(0));
//        System.out.println("Distances: ");
//        System.out.println(Arrays.toString(dd.getLambdas()));
//        System.out.println("Prédécesseurs");
//        System.out.println(Arrays.toString(dd.getPreds()));
    }
}
