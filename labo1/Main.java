import graph.DirectedGraphReader;

import java.io.IOException;

/**
 * Importation de fichiers de données afin de tester l'algorithme de Tarjan
 *
 * @author Valentin Kaelin
 */
public class Main {
    public static void main(String[] args) {
        final String BASE_PATH = "data/";
        final String EXTENSION = ".txt";
        final String[] graphNames = {
                "G500_1",
                "G500_2",
                "G500_3",
                "G1000_1",
                "G1000_2",
                "G1000_3",
                "G2000_1",
                "G2000_2",
                "G2000_3",
                "G4000_1",
                "G4000_2",
                "G4000_3",
        };
        
        for (String filename : graphNames) {
            try {
                Tarjan tarjan = new Tarjan(
                        DirectedGraphReader.fromFile(BASE_PATH + filename + EXTENSION)
                );
                System.out.printf("%-7s -> ", filename);
                System.out.println("Nombre de composantes: " + tarjan.getComposantsCounter());
            } catch (IOException e) {
                System.out.println("Erreur lors de la lectures du fichier " + filename);
                System.out.println(e.getMessage());
            }
        }
    }
}
