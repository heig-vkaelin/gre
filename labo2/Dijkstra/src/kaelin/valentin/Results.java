package kaelin.valentin;

import java.util.LinkedList;

/**
 * Classe permettant de stocker les résultats après un algorithme de Dijkstra
 *
 * @author Valentin Kaelin
 */
public class Results {
    private final LinkedList<Integer> path;
    private final int counter;
    private final long weight;
    
    /**
     * Crée de nouveaux résultats
     *
     * @param path    : plus court chemin
     * @param counter : nombre de sommets traités
     */
    public Results(LinkedList<Integer> path, int counter, long weight) {
        this.path = path;
        this.counter = counter;
        this.weight = weight;
    }
    
    /**
     * @return le nombre de sommets traités
     */
    public int getNbVerticesProcessed() {
        return counter;
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
        return weight;
    }
}
