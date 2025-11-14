package algs;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;

@SuppressWarnings("unchecked")
public class EdgeWeightedDigraph {
    private final int V;
    private int E;
    private final LinkedList<DirectedEdge>[] adj;
    private int[] indegree;

    public EdgeWeightedDigraph(int V) {
        if (V < 0) throw new IllegalArgumentException("Number of vertices must be non-negative");
        this.V = V;
        this.E = 0;
        this.indegree = new int[V];
        adj = (LinkedList<DirectedEdge>[]) new LinkedList[V];
        for (int i = 0; i < V; i++) {
            adj[i] = new LinkedList<>();
        }
    }

    public EdgeWeightedDigraph(Scanner in) {
        if (in == null) throw new IllegalArgumentException("Input is null");
        try {
            this.V = in.nextInt();
            if (this.V < 0) throw new IllegalArgumentException("Number of vertices must be non-negative");
            adj = (LinkedList<DirectedEdge>[]) new LinkedList[this.V];
            for (int i = 0; i < this.V; i++) {
                this.adj[i] = new LinkedList<>();
            }
            int E = in.nextInt();
            if (E < 0) throw new IllegalArgumentException("Number of edges must be non-negative");
            this.indegree = new int[V];
            for (int i = 0; i < E; i++) {
                int v = in.nextInt();
                int w = in.nextInt();
                validateVertex(v);
                validateVertex(w);
                double weight = in.nextDouble();
                DirectedEdge e = new DirectedEdge(v, w, weight);
                addEdge(e);
            }
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    public int V() {
        return V;
    }

    public int E() {
        return E;
    }

    private void validateVertex(int v) {
        if (v < 0 || v > V) throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V - 1));
    }

    public void addEdge(DirectedEdge e) {
        int v = e.from();
        int w = e.to();
        validateVertex(v);
        validateVertex(w);
        adj[v].add(e);
        indegree[w]++;
        E++;
    }

    public Iterable<DirectedEdge> adj(int v) {
        validateVertex(v);
        return adj[v];
    }

    public int outdegree(int v) {
        validateVertex(v);
        return adj[v].size();
    }

    public int indegree(int v) {
        validateVertex(v);
        return indegree[v];
    }

    public Iterable<DirectedEdge> edges() {
        LinkedList<DirectedEdge> list = new LinkedList<>();
        for (int v = 0; v < V; v++) {
            list.addAll(adj[v]);
        }
        return list;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(String.format("%d vertices, %d edges\n", V, E));
        for (int v = 0; v < V; v++) {
            s.append(String.format("%d: ", v));
            for (DirectedEdge e : adj(v)) {
                s.append(e).append(" ");
            }
            s.append("\n");
        }

        return s.toString();
    }

    public static void main(String[] args) throws Exception {
        if (args.length != 1) throw new IllegalArgumentException("Wrong number of arguments");
        File file = new File(args[0]);
        try (Scanner in = new Scanner(file)) {
            EdgeWeightedDigraph G = new EdgeWeightedDigraph(in);
            System.out.println(G);
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("File " + args[0] + " not found");
        }
    }
}
