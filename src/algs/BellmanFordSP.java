package algs;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class BellmanFordSP {
    private double[] distTo;
    private DirectedEdge[] edgeTo;
    private boolean[] onQueue;
    private Queue<Integer> queue;
    private int cost;
    private Iterable<DirectedEdge> cycle;

    public BellmanFordSP(EdgeWeightedDigraph G, int s) {
        distTo = new double[G.V()];
        edgeTo = new DirectedEdge[G.V()];
        onQueue = new boolean[G.V()];
        for (int v = 0; v < G.V(); v++) {
            distTo[v] = Double.POSITIVE_INFINITY;
        }
        distTo[s] = 0.0;
        queue = new Queue<>();
        queue.enqueue(s);
        onQueue[s] = true;
        while (!queue.isEmpty() && !hasNegativeCycle()) {
            int v = queue.dequeue();
            onQueue[v] = false;
            relax(G, v);
        }
    }

    private void relax(EdgeWeightedDigraph G, int v) {
        for (DirectedEdge e : G.adj(v)) {
            int w = e.to();
            if (distTo[w] > distTo[v] + e.weight()) {
                distTo[w] = distTo[v] + e.weight();
                edgeTo[w] = e;
                if (!onQueue[w]) {
                    queue.enqueue(w);
                    onQueue[w] = true;
                }
            }
            if (++cost % G.V() == 0) {
                findNegativeCycle();
                if (hasNegativeCycle()) return;
            }
        }
    }

    public boolean hasNegativeCycle() {
        return cycle != null;
    }

    public Iterable<DirectedEdge> negativeCycle() {
        return cycle;
    }

    private void findNegativeCycle() {
        int V = edgeTo.length;
        EdgeWeightedDigraph spt = new EdgeWeightedDigraph(V);
        for (int v = 0; v < V; v++) {
            if (edgeTo[v] != null) {
                spt.addEdge(edgeTo[v]);
            }
        }
        EWDCycle finder = new EWDCycle(spt);
        cycle = finder.cycle();
    }

    public double distTo(int v) {
        validateVertex(v);
        if (hasNegativeCycle()) throw new UnsupportedOperationException("Negative cycle exists");
        return distTo[v];
    }

    public boolean hasPathTo(int v) {
        validateVertex(v);
        return distTo[v] < Double.POSITIVE_INFINITY;
    }

    public Iterable<DirectedEdge> pathTo(int v) {
        validateVertex(v);
        if (hasNegativeCycle()) throw new UnsupportedOperationException("Negative cycle exists");
        if (!hasPathTo(v)) return null;
        Stack<DirectedEdge> path = new Stack<>();
        for (DirectedEdge e = edgeTo[v]; e != null; e = edgeTo[e.from()]) {
            path.push(e);
        }
        return path;
    }

    private void validateVertex(int v) {
        int V = distTo.length;
        if (v < 0 || v >= V) throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V - 1));
    }

    public static void main(String[] args) throws Exception {
        if (args.length != 2) throw new IllegalArgumentException("Wrong number of arguments");
        File file = new File(args[0]);
        int s = Integer.parseInt(args[1]);
        try (Scanner in = new Scanner(file)) {
            EdgeWeightedDigraph G = new EdgeWeightedDigraph(in);
            BellmanFordSP sp = new BellmanFordSP(G, s);

            if (sp.hasNegativeCycle()) {
                for (DirectedEdge e : sp.negativeCycle()) {
                    System.out.println(e);
                }
            } else {
                for (int v = 0; v < G.V(); v++) {
                    if (sp.hasPathTo(v)) {
                        System.out.printf("%d to %d (%5.2f)  ", s, v, sp.distTo(v));
                        for (DirectedEdge e : sp.pathTo(v)) {
                            System.out.print(e + "   ");
                        }
                        System.out.println();
                    } else {
                        System.out.printf("%d to %d   no path", s, v);
                    }
                }
            }

        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("File " + args[0] + " not found");
        }
    }
}
