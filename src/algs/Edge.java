package algs;

public class Edge implements Comparable<Edge> {
    private final int v;
    private final int w;
    private final double weight;

    public Edge(int v, int w, double weight) {
        if (v < 0) throw new IllegalArgumentException("Vertex has to be non-negative");
        if (w < 0) throw new IllegalArgumentException("Vertex has to be non-negative");
        if (Double.isNaN(weight)) throw new IllegalArgumentException("Weight cannot be NaN");
        this.v = v;
        this.w = w;
        this.weight = weight;
    }

    public int v() {
        return v;
    }

    public int w() {
        return w;
    }

    public double weight() {
        return weight;
    }

    public int either() {
        return v;
    }

    public int other(int vertex) {
        if (vertex == v) return w;
        if (vertex == w) return v;
        throw new IllegalArgumentException("Invalid vertex");
    }

    @Override
    public int compareTo(Edge that) {
        return Double.compare(this.weight(), that.weight());
    }

    @Override
    public String toString() {
        return String.format("(%d-%d %.5f)", v, w, weight);
    }
}
