package kaelin.valentin;

import graph.core.Vertex;
import graph.data.CartesianVertexData;

public class SimpleVertex implements Vertex {
    private final CartesianVertexData data;
    private final int id;
    
    public SimpleVertex(int id, CartesianVertexData data) {
        this.id = id;
        this.data = data;
    }
    
    @Override
    public int id() {
        return id;
    }
    
    public int getX() {
        return data.x;
    }
    
    public int getY() {
        return data.y;
    }
}
