import graph.DirectedGraph;

import java.util.Stack;

public class Tarjan {
    private final String id;
    private final DirectedGraph graph;
    private final int[] dfsnum;
    private final int[] scc;
    private final int[] low;
    private int N;
    private int K;
    private final Stack<Integer> P;
    
    public Tarjan(String id, DirectedGraph graph) {
        this.id = id;
        this.graph = graph;
        
        int nbVertices = graph.getNVertices();
        dfsnum = new int[nbVertices];
        scc = new int[nbVertices];
        low = new int[nbVertices];
        
        N = 0;
        K = 0;
        P = new Stack<>();
        
        solve();
    }
    
    private void solve() {
        for (int vertex = 0; vertex < graph.getNVertices(); vertex++)
            if (scc[vertex] == 0)
                scc(vertex);
    }
    
    private void scc(int vertex) {
        N++;
        
        dfsnum[vertex] = N;
        low[vertex] = N;
        P.push(vertex);
        
        for (int successor : graph.getSuccessorList(vertex)) {
            if (dfsnum[successor] == 0)
                scc(successor);
            
            if (scc[successor] == 0)
                low[vertex] = Math.min(low[vertex], low[successor]);
            
        }
        
        if (low[vertex] == dfsnum[vertex]) {
            K++;
            int top;
            do {
                top = P.pop();
                scc[top] = K;
            } while (top != vertex);
        }
    }
    
    public void displayResult() {
        System.out.println(id + " Nombre de composantes: " + K);
//        for (int i = 0; i < scc.length; i++) {
//            System.out.print("v" + i + " : " + scc[i] + ", ");
//        }
//        System.out.println();
    }
}
