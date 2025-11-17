package algs;

import java.util.EmptyStackException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Stack<E> implements Iterable<E> {
    private Node<E> first;
    private int n;

    private static class Node<E> {
        private E e;
        private Node<E> next;
    }

    public Stack() {
        first = null;
        n = 0;
    }

    public boolean isEmpty() {
        return first == null;
    }

    public int size() {
        return n;
    }

    public void push(E e) {
        Node<E> oldFirst = first;
        first = new Node<E>();
        first.e = e;
        first.next = oldFirst;
        n++;
    }

    public E pop() {
        if (isEmpty()) throw new NoSuchElementException("Stack is empty");
        E e = first.e;
        first = first.next;
        n--;
        return e;
    }

    public E peek() {
        if (isEmpty()) throw new NoSuchElementException("Stack is empty");
        return first.e;
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        for (E e : this) {
            s.append(e);
            s.append(" ");
        }
        return s.toString();
    }

    public Iterator<E> iterator() {
        return new LinkedIterator(first);
    }

    private class LinkedIterator implements Iterator<E> {
        private Node<E> current;

        public LinkedIterator(Node<E> first) {
            current = first;
        }

        public boolean hasNext() {
            return current != null;
        }

        public E next() {
            if (!hasNext()) throw new NoSuchElementException();
            E e = current.e;
            current = current.next;
            return e;
        }
    }
}
