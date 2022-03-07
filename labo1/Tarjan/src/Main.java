import graph.DirectedGraph;
import graph.DirectedGraphReader;

import java.io.IOException;
import java.util.List;
import java.util.Stack;

public class Main {
    static DirectedGraph graph;
    static int[] dfsnum;
    static int[] scc;
    static int[] low;
    static int N = 0;
    static int K = 0;
    static Stack<Integer> P = new Stack<>();
    
    public static void main(String[] args) {
//        String[] graphs = {
//                "G500_1.txt",
//                "G500_2.txt",
//                "G500_3.txt",
//                "G1000_1.txt",
//                "G1000_2.txt",
//                "G1000_3.txt",
//                "G2000_1.txt",
//                "G2000_2.txt",
//                "G2000_3.txt",
//                "G4000_1.txt",
//                "G4000_2.txt",
//                "G4000_3.txt",
//        };
        
        String[] graphs = {"G500_1.txt"};
        
        for (String filename : graphs) {
            try {
                int scc[] = tarjan(DirectedGraphReader.fromFile("data/" + filename));
                printResult(scc);
            } catch (IOException e) {
                System.out.println("ERREUR DE LECTURE");
                System.out.println(e.getMessage());
            }
        }
    }
    
    public static int[] tarjan(DirectedGraph g) {
        graph = g;
        int nbVertices = graph.getNVertices();
        dfsnum = new int[nbVertices];
        scc = new int[nbVertices];
        low = new int[nbVertices];
        
        N = 0;
        K = 0;
        P.empty();
        
        for (int vertex = 0; vertex < nbVertices; vertex++) {
            if (scc[vertex] == 0) {
                scc(vertex);
            }
        }
        return scc;
    }
    
    public static void scc(int u) {
        N++;
        
        dfsnum[u] = N;
        low[u] = N;
        P.push(u);
        
        for (int vertex : graph.getSuccessorList(u)) {
            if (dfsnum[vertex] == 0) {
                scc(vertex);
            }
            
            if (scc[vertex] == 0) {
                low[u] = Math.min(low[u], low[vertex]);
            }
        }
        
        if (low[u] == dfsnum[u]) {
            K++;
            int top;
            do {
                top = P.pop();
                scc[top] = K;
            } while (top != u);
        }
    }
    
    public static void printResult(int[] scc) {
        System.out.println("Nombre de composantes: " + K);
        for (int i = 0; i < scc.length; i++) {
            System.out.print("v" + i + " : " + scc[i] + ", ");
        }
        System.out.println();
    }
}
