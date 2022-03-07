import graph.DirectedGraph;
import graph.DirectedGraphReader;

import java.io.IOException;

public class Main {
    
    public static void main(String[] args) {
//        String[] graphs = {
//                "G500_1.txt",
//                "G500_2.txt",
//                "G500_3.txt",
//                "G1000_1.txt",
//                "G1000_2.txt",
//                "G1000_3.txt",
//                "G2000_1.txt",
//                "G2000_2.txt",
//                "G2000_3.txt",
//                "G4000_1.txt",
//                "G4000_2.txt",
//                "G4000_3.txt",
//        };
        
        String[] graphs = {"G500_1.txt"};
        
        for (String filename : graphs) {
            try {
                DirectedGraph graph = DirectedGraphReader.fromFile("data/" + filename);
                System.out.println(graph.getNVertices());
            } catch (IOException e) {
                System.out.println("ERREUR DE LECTURE");
                System.out.println(e.getMessage());
            }
        }
    }
}
