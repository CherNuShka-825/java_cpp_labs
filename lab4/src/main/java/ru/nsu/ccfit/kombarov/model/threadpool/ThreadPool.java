package ru.nsu.ccfit.kombarov.model.threadpool;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public final class ThreadPool {

    private final Queue<Runnable> tasks = new ArrayDeque<>();
    private final List<Thread> workers = new ArrayList<>();

    private boolean isRunning = false;

    public ThreadPool(int workersCount) {
        if (workersCount <= 0) {
            throw new IllegalArgumentException("workersCount must be positive");
        }

        for (int i = 0; i < workersCount; i++) {
            workers.add(new Thread(this::workerLoop, "Worker-" + (i + 1)));
        }
    }

    public synchronized void start() {
        if (isRunning) {
            return;
        }

        isRunning = true;

        for (Thread worker : workers) {
            worker.start();
        }
    }

    public synchronized void submit(Runnable task) {
        if (!isRunning) {
            return;
        }

        tasks.add(task);
        notifyAll();
    }

    public synchronized int getQueueSize() {
        return tasks.size();
    }

    public void shutdown() {
        synchronized (this) {
            isRunning = false;
            notifyAll();
        }

        for (Thread worker : workers) {
            worker.interrupt();
        }
    }

    private void workerLoop() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                Runnable task = takeTask();

                if (task == null) {
                    return;
                }

                task.run();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private synchronized Runnable takeTask() throws InterruptedException {
        while (tasks.isEmpty() && isRunning) {
            wait();
        }

        if (tasks.isEmpty()) {
            return null;
        }

        return tasks.remove();
    }
}