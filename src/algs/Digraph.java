package algs;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.Scanner;

@SuppressWarnings("unchecked")
public class Digraph {
    private final int V;
    private int E;
    private final LinkedList<Integer>[] adj;
    private final int[] indegree;

    public Digraph(int V) {
        if (V < 0) throw new IllegalArgumentException("Number of vertices must be nonnegative");
        this.V = V;
        this.E = 0;
        indegree = new int[V];
        adj = (LinkedList<Integer>[]) new LinkedList[V];
        for (int v = 0; v < V; v++) {
            adj[v] = new LinkedList<>();
        }
    }

    public Digraph(Scanner in) {
        if (in == null) throw new IllegalArgumentException("Input is null");
        try {
            this.V = in.nextInt();
            if (this.V < 0) throw new IllegalArgumentException("Number of vertices must be nonnegative");
            indegree = new int[V];
            adj = (LinkedList<Integer>[]) new LinkedList[V];
            for (int v = 0; v < V; v++) {
                adj[v] = new LinkedList<>();
            }
            int E = in.nextInt();
            if (E < 0) throw new IllegalArgumentException("Number of edges must be nonnegative");
            for (int i = 0; i < E; i++) {
                int v = in.nextInt();
                int w = in.nextInt();
                addEdge(v, w);
            }
        } catch (InputMismatchException e) {
            throw new IllegalArgumentException("Invalid input", e);
        }
    }

    public int V() {
        return V;
    }

    public int E() {
        return E;
    }

    private void validateVertex(int v) {
        if (v < 0 || v >= V) throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V - 1));
    }

    public void addEdge(int v, int w) {
        validateVertex(v);
        validateVertex(w);
        adj[v].add(w);
        indegree[w]++;
        E++;
    }

    public Iterable<Integer> adj(int v) {
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

    public Digraph reverse() {
        Digraph reverse = new Digraph(V);
        for (int v = 0; v < V; v++) {
            for (int w : adj(v)) {
                reverse.addEdge(w, v);
            }
        }
        return reverse;
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(String.format("%d vertices, %d edges\n", V, E));
        for (int v = 0; v < V; v++) {
            s.append(String.format("%d: ", v));
            for (int w : adj(v)) {
                s.append(String.format("%d ", w));
            }
            s.append("\n");
        }
        return s.toString();
    }

    public static void main(String[] args) throws Exception {
        if (args.length != 1) throw new IllegalArgumentException("Wrong number of arguments");
        File file = new File(args[0]);
        try (Scanner in = new Scanner(file)) {
            Digraph G = new Digraph(in);
            System.out.println(G);
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("File " + args[0] + " not found");
        }
    }
}
