package ru.nsu.ccfit.kombarov.model.factory.controller;

import ru.nsu.ccfit.kombarov.model.factory.entity.*;
import ru.nsu.ccfit.kombarov.model.factory.statistics.FactoryStatistics;
import ru.nsu.ccfit.kombarov.model.factory.storage.Storage;
import ru.nsu.ccfit.kombarov.model.factory.worker.BuildAutoTask;
import ru.nsu.ccfit.kombarov.model.threadpool.ThreadPool;
import ru.nsu.ccfit.kombarov.model.factory.supplier.PartSupplier.DelayProvider;


public final class AutoStorageController implements Runnable {

    private final Storage<Body> bodyStorage;
    private final Storage<Motor> motorStorage;
    private final Storage<Accessory> accessoryStorage;
    private final Storage<Auto> autoStorage;

    private final ThreadPool threadPool;
    private final FactoryStatistics statistics;

    private final DelayProvider delayProvider;

    private int pendingAutos = 0;

    public AutoStorageController(
            Storage<Body> bodyStorage,
            Storage<Motor> motorStorage,
            Storage<Accessory> accessoryStorage,
            Storage<Auto> autoStorage,
            ThreadPool threadPool,
            FactoryStatistics statistics,
            DelayProvider delayProvider
    ) {
        this.bodyStorage = bodyStorage;
        this.motorStorage = motorStorage;
        this.accessoryStorage = accessoryStorage;
        this.autoStorage = autoStorage;
        this.threadPool = threadPool;
        this.statistics = statistics;
        this.delayProvider = delayProvider;
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                createTasksIfNeeded();

                synchronized (this) {
                    wait();
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public synchronized void notifyAutoTaken() {
        notifyAll();
    }

    private synchronized void createTasksIfNeeded() {
        int freePlaces = autoStorage.getCapacity() - autoStorage.getSize();

        int tasksToCreate = freePlaces - pendingAutos;

        for (int i = 0; i < tasksToCreate; i++) {
            pendingAutos++;

            threadPool.submit(new BuildAutoTask(
                    bodyStorage,
                    motorStorage,
                    accessoryStorage,
                    autoStorage,
                    statistics,
                    this,
                    delayProvider
            ));
        }
    }

    public synchronized void autoBuilt() {
        pendingAutos--;
    }

    public synchronized int getPendingAutos() {
        return pendingAutos;
    }
}