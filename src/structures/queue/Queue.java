package structures.queue;

import java.util.LinkedList;
import java.util.NoSuchElementException;

public class Queue<T> {
    private final LinkedList<T> list = new LinkedList<>();

    // Add to the back
    public void enqueue(T item) {
        list.addLast(item);
    }

    // Remove from the front
    public T dequeue() {
        if (isEmpty()) throw new NoSuchElementException("Queue is empty.");
        return list.removeFirst();
    }

    // Peek front without removing
    public T peek() {
        if (isEmpty()) throw new NoSuchElementException("Queue is empty.");
        return list.getFirst();
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }

    public int size() {
        return list.size();
    }

    public void printAll() {
        if (isEmpty()) {
            System.out.println("(Queue is empty)");
        } else {
            for (T item : list) {
                System.out.println(item);
            }
        }
    }

    public LinkedList<T> toList() {
        return new LinkedList<>(list);
    }

    // Optional: clear the queue
    public void clear() {
        list.clear();
    }
}
