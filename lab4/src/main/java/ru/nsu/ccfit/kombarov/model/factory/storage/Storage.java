package ru.nsu.ccfit.kombarov.model.factory.storage;

import java.util.ArrayDeque;
import java.util.Queue;

public final class Storage<T> {

    private final Queue<T> items = new ArrayDeque<>();
    private final int capacity;

    public Storage(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Storage capacity must be positive");
        }

        this.capacity = capacity;
    }

    public synchronized void put(T item) throws InterruptedException {
        while (items.size() >= capacity) {
            wait();
        }

        items.add(item);
        notifyAll();
    }

    public synchronized T take() throws InterruptedException {
        while (items.isEmpty()) {
            wait();
        }

        T item = items.remove();
        notifyAll();
        return item;
    }

    public synchronized int getSize() {
        return items.size();
    }

    public int getCapacity() {
        return capacity;
    }
}