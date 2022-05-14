package kaelin.valentin;

import graph.core.VertexFactory;
import graph.data.CartesianVertexData;

/**
 * Classe permettant de créer facilement un sommet dans un plan cartésien
 *
 * @author Valentin Kaelin
 */
public class SimpleVertexFactory implements VertexFactory<SimpleVertex, CartesianVertexData> {
    @Override
    public SimpleVertex makeVertex(int id, CartesianVertexData additionalData) {
        return new SimpleVertex(id, additionalData.x, additionalData.y);
    }
}
