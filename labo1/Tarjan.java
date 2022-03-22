import graph.DirectedGraph;

import java.util.Stack;

/**
 * Implémentation de l'algorithme de Tarjan
 *
 * @author Valentin Kaelin
 */
public class Tarjan {
    private final DirectedGraph graph;
    // Stocke, pour chaque sommet, la date de début de son traitement.
    private final int[] dfsNum;
    // Stocke, pour chaque sommet, le numéro de sa composante fortement connexe.
    private final int[] scc;
    // Stocke, pour chaque sommet, le numéro de l'ancêtre le plus vieux.
    private final int[] low;
    private int dfsNumCounter;
    private int componentsCounter;
    private final Stack<Integer> visitedVertices;
    
    /**
     * Applique l'algorithme de Tarjan afin de déterminer les composantes fortement
     * connexes du graphe orienté passé en paramètre.
     *
     * @param graph: graphe orienté sur lequel appliquer l'algorithme
     */
    public Tarjan(DirectedGraph graph) {
        if (graph == null)
            throw new RuntimeException(
                    "Le graphe sur lequel appliquer Tarjan est invalide.");
        
        this.graph = graph;
        int nbVertices = graph.getNVertices();
        dfsNum = new int[nbVertices];
        scc = new int[nbVertices];
        low = new int[nbVertices];
        
        dfsNumCounter = 0;
        componentsCounter = 0;
        visitedVertices = new Stack<>();
        
        // Démarre l'algorithme
        solve();
    }
    
    /**
     * Applique l'algorithme de Tarjan à tous les sommets du graphe.
     */
    private void solve() {
        for (int vertex = 0; vertex < graph.getNVertices(); ++vertex)
            if (scc[vertex] == 0)
                scc(vertex);
    }
    
    /**
     * Applique la procédure de l'algorithme de Tarjan au sommet souhaité:
     * détermine la composante fortement connexe du sommet passé en paramètre.
     *
     * @param vertex: numéro du sommet du graphe
     */
    private void scc(int vertex) {
        dfsNumCounter++;
        
        dfsNum[vertex] = dfsNumCounter;
        low[vertex] = dfsNumCounter;
        visitedVertices.push(vertex);
        
        // On vérifie tous les successeurs du sommet choisi
        for (int successor : graph.getSuccessorList(vertex)) {
            // Si le successeur n'a pas encore été visité, on continue la procédure
            // sur celui-ci.
            if (dfsNum[successor] == 0)
                scc(successor);
            
            // Mise-à-jour de l'ancêtre si celui du successeur est plus vieux.
            if (scc[successor] == 0)
                low[vertex] = Math.min(low[vertex], low[successor]);
        }
        
        // Une nouvelle composante fortement connexe a été trouvée
        if (low[vertex] == dfsNum[vertex]) {
            componentsCounter++;
            int topVertex;
            do {
                topVertex = visitedVertices.pop();
                scc[topVertex] = componentsCounter;
            } while (topVertex != vertex);
        }
    }
    
    /**
     * @return le nombre de composantes fortement connexes
     */
    public int getComposantsCounter() {
        return componentsCounter;
    }
}
