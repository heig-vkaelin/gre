package kaelin.valentin;

import graph.core.VertexFactory;
import graph.data.CartesianVertexData;

public class SimpleVertexFactory implements VertexFactory<SimpleVertex, CartesianVertexData> {
    @Override
    public SimpleVertex makeVertex(int id, CartesianVertexData additionalData) {
        return new SimpleVertex(id, additionalData.x, additionalData.y);
    }
}
