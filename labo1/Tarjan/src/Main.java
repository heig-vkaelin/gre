import graph.DirectedGraphReader;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        final String BASE_PATH = "data/";
        final String[] graphs = {
                "G500_1.txt",
                "G500_2.txt",
                "G500_3.txt",
                "G1000_1.txt",
                "G1000_2.txt",
                "G1000_3.txt",
                "G2000_1.txt",
                "G2000_2.txt",
                "G2000_3.txt",
                "G4000_1.txt",
                "G4000_2.txt",
                "G4000_3.txt",
        };
        
        for (String filename : graphs) {
            try {
                Tarjan tarjan = new Tarjan(
                        filename,
                        DirectedGraphReader.fromFile(BASE_PATH + filename)
                );
                tarjan.displayResult();
            } catch (IOException e) {
                System.out.println("Erreur lors de la lectures du fichier " + filename);
                System.out.println(e.getMessage());
            }
        }
    }
}
