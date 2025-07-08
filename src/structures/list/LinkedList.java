package structures.list;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Predicate;

public class LinkedList<T> implements Iterable<T> {

    private class Node {
        T data;
        Node next;

        Node(T item) {
            this.data = item;
        }
    }

    private Node head;
    private int size = 0;

    public void add(T item) {
        Node newNode = new Node(item);
        if (head == null) {
            head = newNode;
        } else {
            Node cur = head;
            while (cur.next != null) cur = cur.next;
            cur.next = newNode;
        }
        size++;
    }

    // Remove node by matching packageId inside toString (old version, you can remove this if unused)
    public boolean remove(String packageId) {
        if (head == null) return false;

        if (head.data.toString().contains(packageId)) {
            head = head.next;
            size--;
            return true;
        }

        Node prev = head;
        Node cur = head.next;

        while (cur != null) {
            if (cur.data.toString().contains(packageId)) {
                prev.next = cur.next;
                size--;
                return true;
            }
            prev = cur;
            cur = cur.next;
        }
        return false;
    }

    // NEW: Remove elements that satisfy the predicate
    public boolean removeIf(Predicate<T> condition) {
        boolean removedAny = false;

        // Remove from head while head matches condition
        while (head != null && condition.test(head.data)) {
            head = head.next;
            size--;
            removedAny = true;
        }

        if (head == null) return removedAny;

        // Now remove nodes after head
        Node prev = head;
        Node current = head.next;

        while (current != null) {
            if (condition.test(current.data)) {
                prev.next = current.next;
                size--;
                removedAny = true;
                current = prev.next; // advance current without moving prev
            } else {
                prev = current;
                current = current.next;
            }
        }

        return removedAny;
    }

    public T find(Predicate<T> condition) {
        Node current = head;
        while (current != null) {
            if (condition.test(current.data)) return current.data;
            current = current.next;
        }
        return null;
    }

    public void printAll() {
        if (head == null) {
            System.out.println("(No items found)");
            return;
        }

        Node cur = head;
        while (cur != null) {
            System.out.println(cur.data);
            cur = cur.next;
        }
    }

    public int size() {
        return size;
    }

    public java.util.List<T> toList() {
        java.util.List<T> list = new java.util.ArrayList<>();
        for (T item : this) {
            list.add(item);
        }
        return list;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            Node current = head;

            public boolean hasNext() {
                return current != null;
            }

            public T next() {
                if (!hasNext()) throw new NoSuchElementException();
                T data = current.data;
                current = current.next;
                return data;
            }
        };
    }
}
