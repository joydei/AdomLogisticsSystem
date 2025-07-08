package structures.hash;

import java.util.ArrayList;
import java.util.List;

import models.Vehicle;

public class HashTable {
    private static class Entry {
        String key;       // Registration number
        Vehicle value;
        Entry next;

        Entry(String key, Vehicle value) {
            this.key = key;
            this.value = value;
        }
    }

    private final int SIZE = 100;
    private final Entry[] buckets;

    public HashTable() {
        buckets = new Entry[SIZE];
    }

    private int hash(String key) {
        return Math.abs(key.hashCode() % SIZE);
    }

    public List<Vehicle> toList() {
        List<Vehicle> list = new ArrayList<>();
        for (Entry bucket : buckets) {
            Entry current = bucket;
            while (current != null) {
                list.add(current.value);
                current = current.next;
            }
        }
        return list;
    }

    public void put(String key, Vehicle value) {
        int index = hash(key);
        Entry head = buckets[index];

        // Check if key already exists
        while (head != null) {
            if (head.key.equals(key)) {
                head.value = value; // Update existing
                return;
            }
            head = head.next;
        }

        // Insert new at the head (chaining)
        Entry newEntry = new Entry(key, value);
        newEntry.next = buckets[index];
        buckets[index] = newEntry;
    }

    public Vehicle get(String key) {
        int index = hash(key);
        Entry head = buckets[index];

        while (head != null) {
            if (head.key.equals(key)) {
                return head.value;
            }
            head = head.next;
        }

        return null; // Not found
    }

    public boolean containsKey(String key) {
        return get(key) != null;
    }

    public void remove(String key) {
        int index = hash(key);
        Entry head = buckets[index];
        Entry prev = null;

        while (head != null) {
            if (head.key.equals(key)) {
                if (prev == null) {
                    buckets[index] = head.next;
                } else {
                    prev.next = head.next;
                }
                return;
            }

            prev = head;
            head = head.next;
        }
    }

    public void printAll() {
        System.out.println("--- Vehicles in HashTable ---");
        for (Entry bucket : buckets) {
            Entry current = bucket;
            while (current != null) {
                System.out.println(current.value);
                current = current.next;
            }
        }
    }
}
