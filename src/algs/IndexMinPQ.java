package algs;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Min Indexed Priority Queue implementation using binary heap.
 */

@SuppressWarnings("unchecked")
public class IndexMinPQ<K extends Comparable<K>> implements Iterable<Integer> {
    private final int maxN;
    private int[] pq;
    private int[] qp;
    private K[] keys;
    private int n;

    public IndexMinPQ(int maxN) {
        if (maxN < 0) throw new IllegalArgumentException();
        this.maxN = maxN;
        n = 0;
        keys = (K[]) new Comparable[maxN + 1];
        pq = new int[maxN + 1];
        qp = new int[maxN + 1];
        for (int i = 0; i < pq.length; i++) qp[i] = -1;
    }

    public int size() {
        return n;
    }

    public boolean isEmpty() {
        return n == 0;
    }

    public boolean contains(int i) {
        validateIndex(i);
        return qp[i] != -1;
    }

    public void insert(int i, K k) {
        validateIndex(i);
        if (contains(i)) throw new IllegalArgumentException("index already exists");
        n++;
        qp[i] = n;
        pq[n] = i;
        keys[i] = k;
        swim(n);
    }

    public int minIndex() {
        if (n == 0) throw new NoSuchElementException();
        return pq[1];
    }

    public K minKey() {
        if (n == 0) throw new NoSuchElementException();
        return keys[pq[1]];
    }

    public int delMin() {
        if (n == 0) throw new NoSuchElementException();
        int min = pq[1];
        swap(1, n--);
        sink(1);
        assert min == pq[n + 1];
        qp[min] = -1;
        keys[min] = null;
        return min;
    }

    public K keyOf(int i) {
        validateIndex(i);
        if (!contains(i)) throw new NoSuchElementException();
        return keys[i];
    }

    public void changeKey(int i, K k) {
        validateIndex(i);
        if (!contains(i)) throw new NoSuchElementException();
        keys[i] = k;
        swim(qp[i]);
        sink(qp[i]);
    }

    public void decreaseKey(int i, K k) {
        validateIndex(i);
        if (!contains(i)) throw new NoSuchElementException();
        if (keys[i].compareTo(k) == 0)
            throw new IllegalArgumentException();
        if (keys[i].compareTo(k) < 0)
            throw new IllegalArgumentException();
        keys[i] = k;
        swim(qp[i]);
    }

    public void increaseKey(int i, K k) {
        validateIndex(i);
        if (!contains(i)) throw new NoSuchElementException();
        if (keys[i].compareTo(k) == 0)
            throw new IllegalArgumentException();
        if (keys[i].compareTo(k) > 0)
            throw new IllegalArgumentException();
        keys[i] = k;
        sink(qp[i]);
    }

    public void delete(int i) {
        validateIndex(i);
        if (!contains(i)) throw new NoSuchElementException();
        int index = qp[i];
        swap(index, n--);
        swim(index);
        sink(index);
        keys[i] = null;
        qp[i] = -1;
    }

    private void validateIndex(int i) {
        if (i < 0) throw new IllegalArgumentException();
        if (i >= maxN) throw new IllegalArgumentException();
    }

    private boolean greater(int i, int j) {
        return keys[pq[i]].compareTo(keys[pq[j]]) > 0;
    }

    private void swap(int i, int j) {
        int swap = pq[i];
        pq[i] = pq[j];
        pq[j] = swap;
        qp[pq[i]] = i;
        qp[pq[j]] = j;
    }

    private void swim(int k) {
        while (k > 1 && greater(k / 2, k)) {
            swap(k, k / 2);
            k = k / 2;
        }
    }

    private void sink(int k) {
        while (2 * k <= n) {
            int j = 2 * k;
            if (j < n && greater(j, j + 1)) j++;
            if (!greater(k, j)) break;
            swap(k, j);
            k = j;
        }
    }

    @Override
    public Iterator<Integer> iterator() {
        return new HeapIterator();
    }

    private class HeapIterator implements Iterator<Integer> {
        private final IndexMinPQ<K> copy;

        public HeapIterator() {
            copy = new IndexMinPQ<>(pq.length - 1);
            for (int i = 1; i <= n; i++) {
                copy.insert(pq[i], keys[pq[i]]);
            }
        }

        public boolean hasNext() {
            return !copy.isEmpty();
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Integer next() {
            if (!hasNext()) throw new NoSuchElementException();
            return copy.delMin();
        }
    }
}
