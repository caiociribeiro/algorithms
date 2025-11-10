package algs;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Computes a MST in an EWG.
 * Lazy implementation of Prim's Algorithm.
 */
public class LazyPrimMST {
    private boolean[] marked;
    private Queue<Edge> mst;
    private MinPQ<Edge> pq;
    private double weight;

    public LazyPrimMST(EdgeWeightedGraph G) {
        pq = new MinPQ<>();
        mst = new Queue<>();
        marked = new boolean[G.V()];
        for (int v = 0; v < G.V(); v++)
            if (!marked[v]) prim(G, v);
    }

    private void prim(EdgeWeightedGraph G, int s) {
        scan(G, s);
        while (!pq.isEmpty()) {
            Edge e = pq.delMin();
            int v = e.either();
            int w = e.other(v);
            assert marked[v] || marked[w];
            if (marked[v] && marked[w]) continue;
            mst.enqueue(e);
            weight += e.weight();
            if (!marked[v]) scan(G, v);
            if (!marked[w]) scan(G, w);
        }
    }

    private void scan(EdgeWeightedGraph G, int v) {
        assert !marked[v];
        marked[v] = true;
        for (Edge e : G.adj(v)) {
            if (!marked[e.other(v)]) pq.insert(e);
        }
    }

    public Iterable<Edge> edges() {
        return mst;
    }

    public double weight() {
        return weight;
    }

    public static void main(String[] args) throws Exception {
        if (args.length != 1) throw new IllegalArgumentException("Wrong number of arguments");
        File file = new File(args[0]);
        try (Scanner in = new Scanner(file)) {
            EdgeWeightedGraph G = new EdgeWeightedGraph(in);
            LazyPrimMST mst = new LazyPrimMST(G);
            for (Edge e : mst.edges()) {
                System.out.println(e);
            }
            System.out.printf("%.5f\n", mst.weight());
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("File " + args[0] + " not found");
        }
    }
}
