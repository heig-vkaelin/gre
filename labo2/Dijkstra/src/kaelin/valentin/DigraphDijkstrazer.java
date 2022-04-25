package kaelin.valentin;

import graph.core.impl.Digraph;
import graph.core.impl.SimpleWeightedEdge;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;
import java.util.PriorityQueue;

/**
 * Implémentation de l'algorithme de Djikstra sur un Digraph.
 *
 * @author Marengo Stéphane
 */
public class DigraphDijkstrazer {
    private final Digraph<SimpleVertex, SimpleWeightedEdge<SimpleVertex>> digraph;
    private PriorityQueue<SimpleVertex> forwardQueue;
    private PriorityQueue<SimpleVertex> backwardQueue;
    private Long[] lambdas;
    private Integer[] preds;
    
    public DigraphDijkstrazer(Digraph<SimpleVertex, SimpleWeightedEdge<SimpleVertex>> digraph) {
        this.digraph = Objects.requireNonNull(digraph, "Le graphe est null.");
    }
    
    public Long[] getLambdas() {
        return lambdas;
    }
    
    public Integer[] getPreds() {
        return preds;
    }
    
    public void solve(SimpleVertex from) {
        initForward(from);
        
        while (!forwardQueue.isEmpty()) {
            SimpleVertex top = forwardQueue.remove();
            if (lambdas[top.id()] == Long.MAX_VALUE)
                continue;
            
            for (var list : digraph.getSuccessorList(top.id())) {
                var succ = list.to();
                if (forwardQueue.contains(succ) && (lambdas[succ.id()] > lambdas[top.id()] + list.weight())) {
                    lambdas[succ.id()] = lambdas[top.id()] + list.weight();
                    preds[succ.id()] = top.id();
                    
                    forwardQueue.remove(succ);
                    forwardQueue.add(succ);
                }
            }
        }
    }
    
    private void findMin(PriorityQueue<SimpleVertex> queue, SimpleVertex vertex, Long[] distances) {
        SimpleVertex top = queue.remove();
        if (distances[top.id()] == Long.MAX_VALUE)
            //continue;
            
            for (var list : digraph.getSuccessorList(top.id())) {
                var succ = list.to();
                if (queue.contains(succ) && (distances[succ.id()] > distances[top.id()] + list.weight())) {
                    distances[succ.id()] = distances[top.id()] + list.weight();
                    preds[succ.id()] = top.id();
                    
                    queue.remove(succ);
                    queue.add(succ);
                }
            }
    }
    
    public void solve(SimpleVertex from, SimpleVertex to) {
        initForward(from);
        initBackward(to);
        
        while (!forwardQueue.isEmpty() && !backwardQueue.isEmpty()) {
            SimpleVertex top = forwardQueue.remove();
            if (lambdas[top.id()] == Long.MAX_VALUE)
                continue;
            
            for (var list : digraph.getSuccessorList(top.id())) {
                var succ = list.to();
                if (forwardQueue.contains(succ) && (lambdas[succ.id()] > lambdas[top.id()] + list.weight())) {
                    lambdas[succ.id()] = lambdas[top.id()] + list.weight();
                    preds[succ.id()] = top.id();
                }
            }
            
            top = backwardQueue.remove();
            if (lambdas[top.id()] == Long.MAX_VALUE)
                continue;
            
            for (var list : digraph.getSuccessorList(top.id())) {
                var pred = list.from();
                if (backwardQueue.contains(pred) && (lambdas[pred.id()] > lambdas[top.id()] + list.weight())) {
                    lambdas[pred.id()] = lambdas[top.id()] + list.weight();
                    preds[pred.id()] = top.id();
                    
                }
            }
        }
    }
    
    private void initForward(SimpleVertex from) {
        lambdas = new Long[digraph.getNVertices()];
        Arrays.fill(lambdas, Long.MAX_VALUE);
        lambdas[from.id()] = 0L;
        
        preds = new Integer[digraph.getNVertices()];
        forwardQueue = createQueue();
    }
    
    private void initBackward(SimpleVertex to) {
        lambdas[to.id()] = 0L;
        backwardQueue = createQueue();
    }
    
    private PriorityQueue<SimpleVertex> createQueue() {
        PriorityQueue<SimpleVertex> pq = new PriorityQueue<>(digraph.getNVertices(),
                Comparator.comparingLong(v -> lambdas[v.id()]));
        pq.addAll(digraph.getVertices());
        
        return pq;
    }
}