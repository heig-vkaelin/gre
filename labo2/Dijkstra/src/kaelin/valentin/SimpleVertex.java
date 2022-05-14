package kaelin.valentin;

import graph.core.Vertex;

/**
 * Classe permettant de modéliser un sommet avec deux coordonnées cartésiennes
 *
 * @author Valentin Kaelin
 */
public class SimpleVertex implements Vertex {
    private final int id;
    
    private final int x;
    private final int y;
    
    /**
     * Crée un nouveau Sommet
     *
     * @param id : identifiant
     * @param x  : coordonnée X
     * @param y  : coordonnée Y
     */
    public SimpleVertex(int id, int x, int y) {
        this.id = id;
        this.x = x;
        this.y = y;
    }
    
    @Override
    public int id() {
        return id;
    }
    
    /**
     * @return la coordonnée X du sommet
     */
    public int getX() {
        return x;
    }
    
    /**
     * @return la coordonnée Y du sommet
     */
    public int getY() {
        return y;
    }
    
    @Override
    public String toString() {
        return String.valueOf(id);
    }
}
