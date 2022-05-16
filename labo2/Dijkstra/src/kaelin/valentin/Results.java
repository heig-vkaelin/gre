package kaelin.valentin;

import java.util.LinkedList;

/**
 * Classe permettant de stocker les résultats après un algorithme de Dijkstra
 *
 * @author Valentin Kaelin
 */
public class Results {
    private final LinkedList<Integer> path;
    private final int verticesCounter;
    private final long pathWeight;
    
    /**
     * Crée de nouveaux résultats
     *
     * @param path            : plus court chemin
     * @param verticesCounter : nombre de sommets traités
     */
    public Results(LinkedList<Integer> path, int verticesCounter, long pathWeight) {
        this.path = path;
        this.verticesCounter = verticesCounter;
        this.pathWeight = pathWeight;
    }
    
    /**
     * @return le nombre de sommets traités
     */
    public int getNbVerticesProcessed() {
        return verticesCounter;
    }
    
    /**
     * @return le plus court chemin sous forme de liste
     */
    public LinkedList<Integer> getShortestPath() {
        return path;
    }
    
    /**
     * @return le poids du plus court chemin
     */
    public long getShortestPathWeight() {
        return pathWeight;
    }
}
