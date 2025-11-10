package algs;

import java.util.Iterator;
import java.util.NoSuchElementException;

@SuppressWarnings("unchecked")
public class MinPQ<K extends Comparable<K>> implements Iterable<K> {
    private K[] pq;
    private int size;

    public MinPQ(int capacity) {
        pq = (K[]) new Comparable[capacity + 1];
        size = 0;
    }

    public MinPQ() {
        this(10);
    }

    public MinPQ(K[] keys) {
        size = keys.length;
        pq = (K[]) new Comparable[keys.length + 1];
        for (int i = 0; i < size; i++) {
            pq[i + 1] = keys[i];
        }
        for (int k = size / 2; k >= 1; k--) {
            sink(k);
        }
        assert isMinHeap();
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void insert(K k) {
        if (size == pq.length - 1) grow(2 * pq.length);
        pq[++size] = k;
        swim(size);
        assert isMinHeap();
    }

    public K removeMin() {
        if (isEmpty()) throw new NoSuchElementException("Priority queue underflow");
        K min = pq[1];
        swap(1, size--);
        sink(1);
        pq[size + 1] = null;
        assert isMinHeap();
        return min;
    }

    private void grow(int capacity) {
        assert capacity > pq.length;
        K[] temp = (K[]) new Comparable[capacity];
        for (int i = 1; i <= pq.length; i++) {
            temp[i] = pq[i];
        }
        pq = temp;
    }

    private boolean isMinHeap() {
        return isMinHeap(1);
    }

    private boolean isMinHeap(int k) {
        if (k > size) return true;
        int left = k * 2;
        int right = k * 2 + 1;
        if (left <= size && greater(k, left)) return false;
        if (right <= size && greater(k, right)) return false;
        return isMinHeap(left) && isMinHeap(right);
    }

    private void swim(int k) {
        while (k > 1 && greater(k / 2, k)) {
            swap(k, k / 2);
            k = k / 2;
        }
    }

    private void sink(int k) {
        while (2 * k <= size) {
            int j = 2 * k;
            if (j < size && greater(j, j + 1)) j++;
            if (greater(j, k)) break;
            swap(k, j);
            k = j;
        }
    }

    private void swap(int i, int j) {
        K tmp = pq[i];
        pq[i] = pq[j];
        pq[j] = tmp;
    }

    private boolean greater(int i, int j) {
        return pq[i].compareTo(pq[j]) > 0;
    }

    @Override
    public Iterator<K> iterator() {
        return new HeapIterator();
    }

    private class HeapIterator implements Iterator<K> {
        private final MinPQ<K> copy;

        public HeapIterator() {
            copy = new MinPQ<>(size);
            for (int i = 1; i <= size; i++) {
                copy.insert(pq[i]);
            }
        }

        public boolean hasNext() {
            return !copy.isEmpty();
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public K next() {
            if (!hasNext()) throw new NoSuchElementException();
            return copy.removeMin();
        }
    }
}
