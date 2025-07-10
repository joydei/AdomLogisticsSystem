package structures.heap;

import java.util.ArrayList;
import java.util.Comparator;

public class MinHeap<T> {

    private final ArrayList<T> heap = new ArrayList<>();
    private final Comparator<T> comparator;

    public MinHeap(Comparator<T> comparator) {
        this.comparator = comparator;
    }

    public void add(T item) {
        heap.add(item);
        heapifyUp(heap.size() - 1);
    }

    public T poll() {
        if (isEmpty()) return null;

        T min = heap.get(0);
        T last = heap.remove(heap.size() - 1);

        if (!heap.isEmpty()) {
            heap.set(0, last);
            heapifyDown(0);
        }

        return min;
    }

    public T peek() {
        return isEmpty() ? null : heap.get(0);
    }

    public boolean isEmpty() {
        return heap.isEmpty();
    }

    public int size() {
        return heap.size();
    }

    public void printAll() {
        if (heap.isEmpty()) {
            System.out.println("(No vehicles in maintenance queue)");
            return;
        }

        for (T item : heap) {
            System.out.println(item);
        }
    }

    private void heapifyUp(int index) {
        while (index > 0) {
            int parent = (index - 1) / 2;
            if (comparator.compare(heap.get(index), heap.get(parent)) < 0) {
                swap(index, parent);
                index = parent;
            } else break;
        }
    }

    private void heapifyDown(int index) {
        int left, right, smallest;

        while ((left = 2 * index + 1) < heap.size()) {
            right = left + 1;
            smallest = index;

            if (comparator.compare(heap.get(left), heap.get(smallest)) < 0) {
                smallest = left;
            }

            if (right < heap.size() && comparator.compare(heap.get(right), heap.get(smallest)) < 0) {
                smallest = right;
            }

            if (smallest != index) {
                swap(index, smallest);
                index = smallest;
            } else break;
        }
    }

    private void swap(int i, int j) {
        T temp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, temp);
    }
}
