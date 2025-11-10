package algs;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;

@SuppressWarnings("unchecked")
public class EdgeWeightedGraph {
    private final int V;
    private int E;
    private final LinkedList<Edge>[] adj;

    public EdgeWeightedGraph(int V) {
        if (V < 0) throw new IllegalArgumentException("Number of vertices must be non-negative");
        this.V = V;
        this.E = 0;
        adj = (LinkedList<Edge>[]) new LinkedList[V];
        for (int i = 0; i < V; i++) {
            adj[i] = new LinkedList<>();
        }
    }

    public EdgeWeightedGraph(Scanner in) {
        if (in == null) throw new IllegalArgumentException("Input is null");
        try {
            this.V = in.nextInt();
            if (this.V < 0) throw new IllegalArgumentException("Number of vertices must be non-negative");
            adj = (LinkedList<Edge>[]) new LinkedList[this.V];
            for (int i = 0; i < this.V; i++) {
                this.adj[i] = new LinkedList<>();
            }
            int E = in.nextInt();
            if (E < 0) throw new IllegalArgumentException("Number of edges must be non-negative");
            for (int i = 0; i < E; i++) {
                int v = in.nextInt();
                int w = in.nextInt();
                double weight = in.nextDouble();
                Edge e = new Edge(v, w, weight);
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

    public void validateVertex(int v) {
        if (v < 0 || v > V) throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V - 1));
    }

    public void validateWeight(double w) {
    }

    public void addEdge(Edge e) {
        validateVertex(e.v());
        validateVertex(e.w());
        adj[e.v()].add(e);
        adj[e.w()].add(e);
        E++;
    }

    public Iterable<Edge> adj(int v) {
        validateVertex(v);
        return adj[v];
    }

    public int degree(int v) {
        validateVertex(v);
        return adj[v].size();
    }

    public Iterable<Edge> edges() {
        LinkedList<Edge> list = new LinkedList<>();
        for (int v = 0; v < V; v++) {
            int selfLoops = 0;
            for (Edge e : adj(v)) {
                if (e.other(v) > v) {
                    list.addLast(e);
                } else if (e.other(v) == v) {
                    if (selfLoops % 2 == 0) list.add(e);
                    selfLoops++;
                }
            }
        }
        return list;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(String.format("%d vertices, %d edges\n", V, E));
        for (int v = 0; v < V; v++) {
            s.append(String.format("%d: ", v));
            for (Edge e : adj(v)) {
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
            EdgeWeightedGraph G = new EdgeWeightedGraph(in);
            System.out.println(G);
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("File " + args[0] + " not found");
        }
    }
}
