package algs;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class KruskalMST {
    private final Queue<Edge> mst = new Queue<>();
    private double weight;

    public KruskalMST(EdgeWeightedGraph G) {
        MinPQ<Edge> pq = new MinPQ<>();
        for (Edge e : G.edges()) {
            pq.insert(e);
        }

        UnionFind uf = new UnionFind(G.V());
        while (!pq.isEmpty() && mst.size() < G.V() - 1) {
            Edge e = pq.delMin();
            int v = e.either();
            int w = e.other(v);
            if (!uf.connected(v, w)) {
                uf.union(v, w);
                mst.enqueue(e);
                weight += e.weight();
            }
        }
    }

    public double weight() {
        return weight;
    }

    public Iterable<Edge> edges() {
        return mst;
    }

    public static void main(String[] args) throws Exception {
        if (args.length != 1) throw new IllegalArgumentException("Wrong number of arguments");
        File file = new File(args[0]);
        try (Scanner in = new Scanner(file)) {
            EdgeWeightedGraph G = new EdgeWeightedGraph(in);
            KruskalMST mst = new KruskalMST(G);
            for (Edge e : mst.edges()) {
                System.out.println(e);
            }
            System.out.printf("%.5f\n", mst.weight());
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("File " + args[0] + " not found");
        }
    }
}
