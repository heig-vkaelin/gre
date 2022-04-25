package kaelin.valentin;

import graph.core.Vertex;

public class SimpleVertex implements Vertex {
    private final int id;
    
    private final int x;
    private final int y;
    
    public SimpleVertex(int id, int x, int y) {
        this.id = id;
        this.x = x;
        this.y = y;
    }
    
    @Override
    public int id() {
        return id;
    }
    
    public int getX() {
        return x;
    }
    
    public int getY() {
        return y;
    }
    
    @Override
    public String toString() {
        return String.valueOf(id);
    }
}
