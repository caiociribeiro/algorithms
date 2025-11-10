package algs;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Queue<T> implements Iterable<T> {
    private Node<T> first;
    private Node<T> last;
    private int size;

    private static class Node<T> {
        T data;
        Node<T> next;
    }

    public Queue() {
        first = null;
        last = null;
        size = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public T peek() {
        if (isEmpty()) throw new NoSuchElementException("Queue is empty");
        return first.data;
    }

    public void enqueue(T data) {
        Node<T> oldLast = last;
        last = new Node<T>();
        last.data = data;
        last.next = null;
        if (isEmpty()) first = last;
        else oldLast.next = last;
        size++;
    }

    public T dequeue() {
        if (isEmpty()) throw new NoSuchElementException("Queue is empty");
        T data = first.data;
        first = first.next;
        size--;
        if (isEmpty()) last = null;
        return data;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (T data : this) {
            s.append(data).append(" ");
        }
        return s.toString();
    }

    public Iterator<T> iterator() {
        return new LinkedIterator(first);
    }

    private class LinkedIterator implements Iterator<T> {
        private Node<T> current;

        public LinkedIterator(Node<T> first) {
            current = first;
        }

        public boolean hasNext() {
            return current != null;
        }

        public T next() {
            if (!hasNext()) throw new NoSuchElementException();
            T data = current.data;
            current = current.next;
            return data;
        }
    }
}
