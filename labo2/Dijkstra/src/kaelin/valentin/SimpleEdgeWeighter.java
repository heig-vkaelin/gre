package kaelin.valentin;

import graph.core.EdgeWeighter;

public class SimpleEdgeWeighter implements EdgeWeighter<SimpleVertex> {
    @Override
    public long weight(SimpleVertex from, SimpleVertex to) {
        int dx = to.getX() - from.getX();
        int dy = to.getY() - from.getY();
        return Math.round(Math.sqrt(dx * dx + dy * dy));
    }
}
