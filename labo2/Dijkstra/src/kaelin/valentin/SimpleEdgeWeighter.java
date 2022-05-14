package kaelin.valentin;

import graph.core.EdgeWeighter;

/**
 * Classe représentant le poids d’un arc correspond à la distance euclidienne
 * entre ses deux extrémités (distance arrondie à l’entier le plus proche).
 *
 * @author Valentin Kaelin
 */
public class SimpleEdgeWeighter implements EdgeWeighter<SimpleVertex> {
    @Override
    public long weight(SimpleVertex from, SimpleVertex to) {
        int dx = to.getX() - from.getX();
        int dy = to.getY() - from.getY();
        return Math.round(Math.sqrt(dx * dx + dy * dy));
    }
}
